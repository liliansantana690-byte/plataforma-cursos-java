package com.plataforma.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String URL  = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USER");
    private static final String PASS = System.getenv("DB_PASSWORD");

    public static Connection conectar() throws SQLException {
        if (URL == null || USER == null || PASS == null) {
            throw new IllegalStateException(
                "Configure as variáveis DB_URL, DB_USER e DB_PASSWORD."
            );
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
