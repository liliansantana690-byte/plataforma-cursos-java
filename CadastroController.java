package com.plataforma.controller;

import com.plataforma.dao.UsuarioDAO;
import com.plataforma.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

public class CadastroController {

    @FXML private TextField campoNome;
    @FXML private TextField campoEmail;
    @FXML private PasswordField campoSenha;
    @FXML private Label lblErro;
    @FXML private Label lblSucesso;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void cadastrar() {
        String nome  = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = campoSenha.getText();

        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            lblErro.setText("Preencha todos os campos.");
            return;
        }

        if (senha.length() < 6) {
            lblErro.setText("A senha deve ter no mínimo 6 caracteres.");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);

        boolean sucesso = usuarioDAO.cadastrar(usuario);

        if (sucesso) {
            lblSucesso.setText("Cadastro realizado! Faça login.");
            lblErro.setText("");
        } else {
            lblErro.setText("Email já cadastrado ou erro ao salvar.");
        }
    }

    @FXML
    private void voltarLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/plataforma/login.fxml"));
            Stage stage = (Stage) campoNome.getScene().getWindow();
            stage.setScene(new Scene(loader.load(), 400, 350));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
