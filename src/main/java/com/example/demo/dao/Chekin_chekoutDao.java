package com.example.demo.dao;

import com.example.demo.db.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Chekin_chekoutDao {

    public void registrarCheckIn(int idReserva, String nomeRecepcionista) {
        String sql = "INSERT INTO checkin_checkout (id_reserva, data_checkin_real, responsavel_checkin, status_hospedagem) VALUES (?, ?, ?, 'HOSPEDADO')";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idReserva);
            stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(3, nomeRecepcionista);

            stmt.executeUpdate();
            System.out.println("check-in realizado com sucesso pelo funciona rio: " + nomeRecepcionista);

        } catch (SQLException e) {
            System.out.println("erro ao registrar o check-in.");
            e.printStackTrace();
        }
    }

    public void registrarCheckOut(int idReserva, String nomeRecepcionista) {
        String sql = "UPDATE checkin_checkout SET data_checkout_real = ?, responsavel_checkout = ?, status_hospedagem = 'CHECKOUT_REALIZADO' WHERE id_reserva = ? AND data_checkout_real IS NULL";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(2, nomeRecepcionista);
            stmt.setInt(3, idReserva);

            int linhas = stmt.executeUpdate();
            if (linhas > 0) {
                System.out.println("check-out realizado com sucesso!");
            } else {
                System.out.println("falha no check-out, verifique se o check-in foi feito ou se o check-out já ocorreu.");
            }

        } catch (SQLException e) {
            System.out.println("erro ao registrar o check-out.");
            e.printStackTrace();
        }
    }
}
