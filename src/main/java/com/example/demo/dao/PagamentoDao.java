package com.example.demo.dao;


import com.example.demo.db.DB;
import com.example.demo.entities.Pagamento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PagamentoDao {

    public void registrar(Pagamento pagamento) {
        String sql = "INSERT INTO pagamento (id_reserva, forma_pagamento, valor_total, valor_pago, parcelas, data_pagamento) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pagamento.getIdReserva());
            stmt.setString(2, pagamento.getFormaPagamento()); // 'DINHEIRO', 'PIX', 'CARTAO_CREDITO', 'CARTAO_DEBITO'
            stmt.setDouble(3, pagamento.getValorTotal());
            stmt.setDouble(4, pagamento.getValorPago());
            stmt.setInt(5, pagamento.getParcelas());

            // Registra a data atual do pagamento
            stmt.setTimestamp(6, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));

            stmt.executeUpdate();
            System.out.println("pagamento registrado com sucesso!");

        } catch (SQLException e) {
            System.out.println("erro ao registrar pagamento.");
            e.printStackTrace();
        }
    }
}