package com.plataforma.dao;

import com.plataforma.model.Curso;
import com.plataforma.util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    public List<Curso> listarTodos() {
        String sql = "SELECT * FROM curso ORDER BY titulo";
        List<Curso> lista = new ArrayList<>();
        try (Connection conn = ConexaoBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Curso(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("descricao"),
                    rs.getInt("carga_horaria")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar cursos: " + e.getMessage());
        }
        return lista;
    }
}
