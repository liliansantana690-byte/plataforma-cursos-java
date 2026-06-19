package com.plataforma.controller;

import com.plataforma.dao.UsuarioDAO;
import com.plataforma.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private Label lblErro;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void fazerLogin() {
        String email = campoEmail.getText().trim();
        String senha = campoSenha.getText();

        if (email.isEmpty() || senha.isEmpty()) {
            lblErro.setText("Preencha todos os campos.");
            return;
        }

        Usuario usuario = usuarioDAO.autenticar(email, senha);

        if (usuario != null) {
            abrirTelaCursos(usuario);
        } else {
            lblErro.setText("Email ou senha incorretos.");
        }
    }

    @FXML
    private void abrirCadastro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/plataforma/cadastro.fxml"));
            Stage stage = (Stage) campoEmail.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 400));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirTelaCursos(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/plataforma/cursos.fxml"));
            CursosController controller = new CursosController();
            controller.setUsuario(usuario);
            loader.setController(controller);
            Stage stage = (Stage) campoEmail.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 700, 500));
            stage.setTitle("Plataforma de Cursos — " + usuario.getNome());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
