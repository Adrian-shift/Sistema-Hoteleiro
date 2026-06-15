package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.entities.Hospede;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HospedeDAO {

    //  metodo para inserir um novo hospede no SISTEMA

    public void Cadastrar(Hospede hospede) {
        String sql = "INSERT INTO hospede (nome_completo, cpf, email, telefone) VALUES (?, ?, ?, ?)";

        // abre a conexao e prepara o comando
        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hospede.getNome_Completo()); // substitui as interrogações pelos dados reais dos hospedes
            stmt.setString(2, hospede.getCpf());
            stmt.setString(3, hospede.getEmail());
            stmt.setString(4, hospede.getTelefone());

            stmt.executeUpdate();
            System.out.println(" hospede cadastrado com sucesso !!!");

        } catch (SQLException e) {
            System.out.println("erro ao salvar os dados do hospede.");
            e.printStackTrace();
        }
    }
}
