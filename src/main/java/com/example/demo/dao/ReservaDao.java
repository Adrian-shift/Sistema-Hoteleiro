package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.model.Reserva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReservaDao {

    public void salvar(Reserva reserva) {
        String sqlReserva = "INSERT INTO reserva (id_hospede, id_quarto, data_checkin_prevista, data_checkout_prevista, valor_total) VALUES (?, ?, ?, ?, ?)";
        String sqlQuarto = "UPDATE quarto SET status_ocupacao = 'OCUPADO' WHERE id_quarto = ?"; // O id_quarto aciona a reserva || o sistema depois atualiza o status do quarto pelo id.

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmtReserva = conn.prepareStatement(sqlReserva);
                 PreparedStatement stmtQuarto = conn.prepareStatement(sqlQuarto)) {

                // salva a Reserva
                stmtReserva.setInt(1, reserva.getIdHospede());
                stmtReserva.setInt(2, reserva.getIdQuarto());
                stmtReserva.setDate(3, java.sql.Date.valueOf(reserva.getDataCheckinPrevista()));
                stmtReserva.setDate(4, java.sql.Date.valueOf(reserva.getDataCheckoutPrevista()));
                stmtReserva.setDouble(5, reserva.getValorTotal());
                stmtReserva.executeUpdate();

                // atualiza o quarto para 'OCUPADO'
                stmtQuarto.setInt(1, reserva.getIdQuarto());
                stmtQuarto.executeUpdate();

                conn.commit();
                System.out.println("reserva efetuada com sucesso!");

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("erro ao salvar a reserva.");
            e.printStackTrace();
        }
    }
}
