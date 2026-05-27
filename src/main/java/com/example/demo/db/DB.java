package com.example.demo.db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * CLASSE DE CONEXÃO (SINGLETON PATTERN CONCEPT)
 * * CONCEITO DIDÁTICO: Concentra a responsabilidade de abrir, fechar e gerenciar
 * a conexão com o banco de dados. Evita código duplicado e conexões "órfãs" abertas na memória.
 */
public class DB {

    // Objeto de conexão estático: garante que o aplicativo possa compartilhar a mesma conexão
    private static Connection conn = null;

    /**
     * Abre e retorna a conexão ativa com o banco de dados.
     */
    public static Connection getConnection() {
        if (conn == null) { // Só abre se não existir uma conexão ativa (Economia de recursos)
            try {
                Properties props = loadProperties(); // Carrega os dados do arquivo de texto
                String url = props.getProperty("dburl"); // jdbc:mysql://localhost:3306/nome_banco
                conn = DriverManager.getConnection(url, props);
            } catch (SQLException e) {
                // Captura o erro nativo do SQL e joga na nossa exceção customizada amigável
                throw new db.DbException(e.getMessage());
            }
        }
        return conn;
    }

    /**
     * Fecha a conexão de forma segura.
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                conn = null; // Reseta para nulo para que possa ser reaberta futuramente se necessário
            } catch (SQLException e) {
                throw new db.DbException(e.getMessage());
            }
        }
    }

    /**
     * MÉTODO AUXILIAR: Lê o arquivo 'db.properties' para isolar dados sensíveis
     * (como usuário e senha do banco) do código-fonte. Boa prática de segurança de mercado.
     */
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties props = new Properties();
            props.load(fs);
            return props;
        } catch (IOException e) {
            throw new db.DbException("Erro ao ler credenciais do banco: " + e.getMessage());
        }
    }

    // Métodos utilitários para fechar Statements e ResultSets com segurança, evitando vazamento de memória (Memory Leak)
    public static void closeStatment(Statement st) {
        if (st != null) {
            try { st.close(); } catch (SQLException e) { throw new db.DbException(e.getMessage()); }
        }
    }

    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try { rs.close(); } catch (SQLException e) { throw new db.DbException(e.getMessage()); }
        }
    }
}
