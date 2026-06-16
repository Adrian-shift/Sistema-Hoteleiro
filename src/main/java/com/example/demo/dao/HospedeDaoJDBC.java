package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.db.DbException;
import com.example.demo.db.DbIntegrityException;
import com.example.demo.entities.Hospede;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class HospedeDaoJDBC implements HospedeDao {

    private final Connection conn;

    public HospedeDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Hospede hospede) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO hospede "
                            + "(nome_completo, cpf, email, telefone, data_cadastro, status) "
                            + "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, hospede.getNomeCompleto());
            st.setString(2, hospede.getCpf());
            st.setString(3, hospede.getEmail());
            st.setString(4, hospede.getTelefone());
            setNullableTimestamp(st, 5, hospede.getDataCadastro());
            st.setString(6, hospede.getStatus());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Erro inesperado: nenhuma linha inserida em hospede.");
            }

            ResultSet rs = null;
            try {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    hospede.setIdHospede(rs.getInt(1));
                }
            } finally {
                DB.closeResultSet(rs);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao inserir hospede.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir hospede.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Hospede hospede) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE hospede "
                            + "SET nome_completo = ?, "
                            + "cpf = ?, "
                            + "email = ?, "
                            + "telefone = ?, "
                            + "data_cadastro = ?, "
                            + "status = ? "
                            + "WHERE id_hospede = ?"
            );

            st.setString(1, hospede.getNomeCompleto());
            st.setString(2, hospede.getCpf());
            st.setString(3, hospede.getEmail());
            st.setString(4, hospede.getTelefone());
            setNullableTimestamp(st, 5, hospede.getDataCadastro());
            st.setString(6, hospede.getStatus());
            st.setInt(7, hospede.getIdHospede());

            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao atualizar hospede.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar hospede.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM hospede WHERE id_hospede = ?"
            );
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao remover hospede.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao remover hospede.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Hospede findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_hospede, nome_completo, cpf, email, telefone, data_cadastro, status "
                            + "FROM hospede "
                            + "WHERE id_hospede = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateHospede(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar hospede por id.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Hospede findByCpf(String cpf) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_hospede, nome_completo, cpf, email, telefone, data_cadastro, status "
                            + "FROM hospede "
                            + "WHERE cpf = ?"
            );
            st.setString(1, cpf);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateHospede(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar hospede por CPF.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Hospede findByEmail(String email) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_hospede, nome_completo, cpf, email, telefone, data_cadastro, status "
                            + "FROM hospede "
                            + "WHERE email = ?"
            );
            st.setString(1, email);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateHospede(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar hospede por e-mail.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Hospede> findByName(String name) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_hospede, nome_completo, cpf, email, telefone, data_cadastro, status "
                            + "FROM hospede "
                            + "WHERE LOWER(nome_completo) LIKE LOWER(?) "
                            + "ORDER BY nome_completo"
            );
            st.setString(1, "%" + name + "%");
            rs = st.executeQuery();

            List<Hospede> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateHospede(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar hospedes por nome.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Hospede> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_hospede, nome_completo, cpf, email, telefone, data_cadastro, status "
                            + "FROM hospede "
                            + "ORDER BY nome_completo"
            );
            rs = st.executeQuery();

            List<Hospede> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateHospede(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao listar hospedes.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Hospede instantiateHospede(ResultSet rs) throws SQLException {
        return new Hospede(
                rs.getInt("id_hospede"),
                rs.getString("nome_completo"),
                rs.getString("cpf"),
                rs.getString("email"),
                rs.getString("telefone"),
                getNullableTimestamp(rs, "data_cadastro"),
                rs.getString("status")
        );
    }

    private static void setNullableTimestamp(PreparedStatement st, int index, java.time.LocalDateTime value)
            throws SQLException {
        if (value == null) {
            st.setNull(index, Types.TIMESTAMP);
        } else {
            st.setTimestamp(index, Timestamp.valueOf(value));
        }
    }

    private static java.time.LocalDateTime getNullableTimestamp(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return timestamp == null ? null : timestamp.toLocalDateTime();
    }
}
