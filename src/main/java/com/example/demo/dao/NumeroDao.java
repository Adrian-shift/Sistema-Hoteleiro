package com.example.demo.dao;

import com.example.demo.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NumeroDao {

    public List<String> listarNumerosDisponiveis() {
        String sql = "SELECT numero FROM quarto WHERE status_ocupacao = 'LIVRE' AND ativo = TRUE"; // Puxa o numero da tabela quarto filtrando por LIVRE
        List<String> numeros = new ArrayList<>();

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                numeros.add(rs.getString("numero: "));
            }

        } catch (SQLException e) {
            System.out.println("erro ao buscar a lista de numeros livres.");
            e.printStackTrace();
        }

        return numeros;
    }
}