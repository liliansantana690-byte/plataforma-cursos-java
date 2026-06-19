package com.plataforma.controller;

import com.plataforma.dao.CursoDAO;
import com.plataforma.dao.MatriculaDAO;
import com.plataforma.model.Curso;
import com.plataforma.model.MatriculaCurso;
import com.plataforma.model.Usuario;
import com.plataforma.util.GeradorCertificadoPDF;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class CursosController {

    @FXML private ListView<Curso> listaCursos;
    @FXML private ListView<MatriculaCurso> listaMeusCursos;
    @FXML private Label lblProgresso;
    @FXML private Label lblUsuario;
    @FXML private Label lblMensagem;

    private final CursoDAO cursoDAO         = new CursoDAO();
    private final MatriculaDAO matriculaDAO = new MatriculaDAO();
    private Usuario usuarioLogado;

    public void setUsuario(Usuario usuario) {
        this.usuarioLogado = usuario;
    }

    @FXML
    public void initialize() {
        if (usuarioLogado != null) carregarDados();
    }

    public void carregarDados() {
        lblUsuario.setText("Olá, " + usuarioLogado.getNome());

        listaCursos.setItems(FXCollections.observableArrayList(cursoDAO.listarTodos()));

        List<MatriculaCurso> meusCursos = matriculaDAO.listarCursosPorUsuario(usuarioLogado.getId());
        listaMeusCursos.setItems(FXCollections.observableArrayList(meusCursos));
        listaMeusCursos.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(MatriculaCurso item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getTitulo() + " — " + item.getStatus());
            }
        });

        int total = matriculaDAO.contarConcluidos(usuarioLogado.getId());
        lblProgresso.setText("Cursos concluídos: " + total);
    }

    @FXML
    private void matricular() {
        Curso selecionado = listaCursos.getSelectionModel().getSelectedItem();
        if (selecionado == null) { lblMensagem.setText("Selecione um curso."); return; }

        if (matriculaDAO.jaMatriculado(usuarioLogado.getId(), selecionado.getId())) {
            lblMensagem.setText("Você já está matriculado neste curso.");
            return;
        }

        boolean ok = matriculaDAO.matricular(usuarioLogado.getId(), selecionado.getId());
        lblMensagem.setText(ok ? "Matrícula realizada!" : "Erro ao matricular.");
        carregarDados();
    }

    @FXML
    private void concluirCurso() {
        MatriculaCurso selecionado = listaMeusCursos.getSelectionModel().getSelectedItem();
        if (selecionado == null) { lblMensagem.setText("Selecione um curso em 'Meus Cursos'."); return; }
        if (selecionado.isConcluido()) { lblMensagem.setText("Curso já concluído."); return; }

        matriculaDAO.marcarConcluido(usuarioLogado.getId(), selecionado.getId());

        String caminho = "certificados/pdf/cert_" + usuarioLogado.getId() + "_" + selecionado.getId() + ".pdf";
        GeradorCertificadoPDF.gerar(usuarioLogado.getNome(), selecionado.getTitulo(), selecionado.getCargaHoraria(), caminho);

        lblMensagem.setText("Concluído! Certificado em: " + caminho);
        carregarDados();
    }

    @FXML
    private void abrirRanking() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/plataforma/ranking.fxml"));
            RankingController controller = new RankingController();
            loader.setController(controller);
            Stage stage = new Stage();
            stage.setTitle("Ranking de Usuários");
            stage.setScene(new Scene(loader.load(), 500, 400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
