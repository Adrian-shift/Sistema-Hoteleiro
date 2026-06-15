package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.entities.Endereco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnderecoDao {

    // Método para CADASTRAR o endereço e vinculá-lo a um hóspede
    public void cadastrar(Endereco endereco, int idHospede) {
        // O campo hospede_id é a chave estrangeira que liga este endereço ao cliente correto
        String sql = "INSERT INTO enderecos (rua, numero, bairro, cidade, estado, cep, hospede_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getEstado());
            stmt.setString(6, endereco.getCep());
            stmt.setInt(7, idHospede);

            stmt.executeUpdate();
            System.out.println("Endereço salvo com sucesso no banco de dados!");

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar o endereço.");
            e.printStackTrace();
        }
    }

    // Método para BUSCAR o endereço de um hóspede específico
    public Endereco buscarPorHospede(int idHospede) {
        String sql = "SELECT * FROM enderecos WHERE hospede_id = ?";
        Endereco endereco = null;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idHospede);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    endereco = new Endereco();
                    endereco.setId(rs.getInt("id"));
                    endereco.setRua(rs.getString("rua"));
                    endereco.setNumero(rs.getString("numero"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setEstado(rs.getString("estado"));
                    endereco.setCep(rs.getString("cep"));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar o endereço do hóspede.");
            e.printStackTrace();
        }

        return endereco;
    }

    // Método para ATUALIZAR um endereço existente
    public void atualizar(Endereco endereco, int idHospede) {
        String sql = "UPDATE enderecos SET rua = ?, numero = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE hospede_id = ?";

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, endereco.getRua());
            stmt.setString(2, endereco.getNumero());
            stmt.setString(3, endereco.getBairro());
            stmt.setString(4, endereco.getCidade());
            stmt.setString(5, endereco.getEstado());
            stmt.setString(6, endereco.getCep());
            stmt.setInt(7, idHospede);

            stmt.executeUpdate();
            System.out.println("Endereço atualizado com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o endereço.");
            e.printStackTrace();
        }
    }
}

