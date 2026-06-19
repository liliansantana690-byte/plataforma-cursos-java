package com.plataforma.dao;

import com.plataforma.model.Curso;
import com.plataforma.model.MatriculaCurso;
import com.plataforma.model.UsuarioRanking;
import com.plataforma.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAO {

    public boolean matricular(int usuarioId, int cursoId) {
        String sql = "INSERT INTO matricula (usuario_id, curso_id, data_inicio) VALUES (?, ?, CURRENT_DATE)";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, cursoId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao matricular: " + e.getMessage());
            return false;
        }
    }

    public boolean jaMatriculado(int usuarioId, int cursoId) {
        String sql = "SELECT id FROM matricula WHERE usuario_id = ? AND curso_id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, cursoId);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            return false;
        }
    }

    public void marcarConcluido(int usuarioId, int cursoId) {
        String sql = "UPDATE matricula SET concluido = TRUE, data_conclusao = CURRENT_DATE WHERE usuario_id = ? AND curso_id = ?";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            stmt.setInt(2, cursoId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao concluir: " + e.getMessage());
        }
    }

    public int contarConcluidos(int usuarioId) {
        String sql = "SELECT COUNT(*) FROM matricula WHERE usuario_id = ? AND concluido = TRUE";
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1);

        } catch (SQLException e) {
            System.err.println("Erro ao contar: " + e.getMessage());
        }
        return 0;
    }

    public List<MatriculaCurso> listarCursosPorUsuario(int usuarioId) {
        String sql = """
            SELECT c.id, c.titulo, c.descricao, c.carga_horaria, m.concluido
            FROM matricula m
            JOIN curso c ON c.id = m.curso_id
            WHERE m.usuario_id = ?
            ORDER BY c.titulo
            """;
        List<MatriculaCurso> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new MatriculaCurso(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getInt("carga_horaria"),
                    rs.getBoolean("concluido")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar: " + e.getMessage());
        }
        return lista;
    }

    public List<UsuarioRanking> listarRanking() {
        String sql = """
            SELECT u.id, u.nome, COUNT(m.id) AS total_concluidos
            FROM usuario u
            LEFT JOIN matricula m ON u.id = m.usuario_id AND m.concluido = TRUE
            GROUP BY u.id, u.nome
            ORDER BY total_concluidos DESC
            """;
        List<UsuarioRanking> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new UsuarioRanking(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getInt("total_concluidos")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar ranking: " + e.getMessage());
        }
        return lista;
    }
}
