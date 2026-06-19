package com.plataforma.dao;

import com.plataforma.model.Usuario;
import com.plataforma.util.ConexaoBD;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.*;

public class UsuarioDAO {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuario (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, encoder.encode(usuario.getSenha()));
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
            return false;
        }
    }

    public Usuario autenticar(String email, String senha) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String senhaSalva = rs.getString("senha");
                if (encoder.matches(senha, senhaSalva)) {
                    return new Usuario(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("email"),
                        senhaSalva
                    );
                }
            }
            return null;

        } catch (SQLException e) {
            System.err.println("Erro ao autenticar: " + e.getMessage());
            return null;
        }
    }
}
