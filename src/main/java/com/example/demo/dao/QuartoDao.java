package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.model.Quarto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class QuartoDao {

    public void cadastrar(Quarto quarto) {

        String sql = "INSERT INTO quarto (id_tipo_quarto, numero) VALUES (?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, quarto.getIdTipoQuarto());
            stmt.setString(2, quarto.getNumero());
            stmt.executeUpdate();
            System.out.println( quarto.getNumero() + " cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("erro ao cadastrar o quarto.");
            e.printStackTrace();
        }
    }

    public void atualizarStatus(String numeroQuarto, String novoStatus) {
        String sql = "UPDATE quarto SET status_ocupacao = ? WHERE numero = ?";  // status aceitos no SQL: 'LIVRE', 'OCUPADO', 'LIMPEZA', 'MANUTENCAO'

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setString(2, numeroQuarto);

            stmt.executeUpdate();
            System.out.println("status do quarto " + numeroQuarto + " atualizado para " + novoStatus);

        } catch (SQLException e) {
            System.out.println("erro ao atualizar o status do quarto.");
            e.printStackTrace();
        }
    }
}