package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.entities.Tipo_Quarto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Tipo_quartoDao {

    public void cadastrar(Tipo_Quarto tipoQuarto) {
        String sql = "INSERT INTO tipo_quarto (nome, descricao, valor_diaria, capacidade) VALUES (?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tipoQuarto.getNome());
            stmt.setString(2, tipoQuarto.getDescricao());
            stmt.setDouble(3, tipoQuarto.getValorDiaria());
            stmt.setInt(4, tipoQuarto.getCapacidade());

            stmt.executeUpdate();
            System.out.println("tipo de quarto: " + tipoQuarto.getNome() + ", cadastrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("erro ao cadastrar o tipo de quarto");
            e.printStackTrace();
        }
    }
}