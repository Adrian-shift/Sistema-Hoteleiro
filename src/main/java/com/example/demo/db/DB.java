package com.example.demo.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class DB {

    private static Connection conn = null;

    private DB() {
    }

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                Properties props = loadProperties();
                String url = requireProperty(props, "dburl");
                conn = DriverManager.getConnection(url, props);
            }
            return conn;
        } catch (SQLException e) {
            throw new DbException("Erro ao abrir conexao com o banco de dados.", e);
        }
    }

    public static void closeConnection() {
        closeConnection(conn);
        conn = null;
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException("Erro ao fechar conexao com o banco de dados.", e);
            }
        }
    }

    public static void closeStatement(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DbException("Erro ao fechar Statement.", e);
            }
        }
    }

    /**
     * @deprecated Use {@link #closeStatement(Statement)}.
     */
    @Deprecated
    public static void closeStatment(Statement statement) {
        closeStatement(statement);
    }

    public static void closeResultSet(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DbException("Erro ao fechar ResultSet.", e);
            }
        }
    }

    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            requireProperty(props, "user");
            requireProperty(props, "password");
            requireProperty(props, "dburl");
            return props;
        } catch (IOException e) {
            throw new DbException("Erro ao ler o arquivo db.properties.", e);
        }
    }

    private static String requireProperty(Properties props, String name) {
        String value = props.getProperty(name);
        if (value == null || value.trim().isEmpty()) {
            throw new DbException("Propriedade obrigatoria ausente no db.properties: " + name);
        }
        return value;
    }
}
