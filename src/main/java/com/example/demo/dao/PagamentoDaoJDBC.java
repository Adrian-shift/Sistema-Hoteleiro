package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.db.DbException;
import com.example.demo.db.DbIntegrityException;
import com.example.demo.entities.Pagamento;
import com.example.demo.entities.Reserva;

import java.math.BigDecimal;
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

public class PagamentoDaoJDBC implements PagamentoDao {

    private final Connection conn;

    public PagamentoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Pagamento pagamento) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO pagamento "
                            + "(id_reserva, forma_pagamento, status_pagamento, valor_total, "
                            + "valor_pago, parcelas, data_pagamento) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, pagamento.getReserva().getIdReserva());
            st.setString(2, pagamento.getFormaPagamento());
            st.setString(3, pagamento.getStatusPagamento());
            st.setBigDecimal(4, pagamento.getValorTotal());
            st.setBigDecimal(5, pagamento.getValorPago());
            st.setInt(6, pagamento.getParcelas());
            setNullableTimestamp(st, 7, pagamento.getDataPagamento());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Erro inesperado: nenhuma linha inserida em pagamento.");
            }

            ResultSet rs = null;
            try {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    pagamento.setIdPagamento(rs.getInt(1));
                }
            } finally {
                DB.closeResultSet(rs);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao inserir pagamento.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir pagamento.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Pagamento pagamento) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE pagamento "
                            + "SET id_reserva = ?, "
                            + "forma_pagamento = ?, "
                            + "status_pagamento = ?, "
                            + "valor_total = ?, "
                            + "valor_pago = ?, "
                            + "parcelas = ?, "
                            + "data_pagamento = ? "
                            + "WHERE id_pagamento = ?"
            );

            st.setInt(1, pagamento.getReserva().getIdReserva());
            st.setString(2, pagamento.getFormaPagamento());
            st.setString(3, pagamento.getStatusPagamento());
            st.setBigDecimal(4, pagamento.getValorTotal());
            st.setBigDecimal(5, pagamento.getValorPago());
            st.setInt(6, pagamento.getParcelas());
            setNullableTimestamp(st, 7, pagamento.getDataPagamento());
            st.setInt(8, pagamento.getIdPagamento());

            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao atualizar pagamento.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar pagamento.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM pagamento WHERE id_pagamento = ?"
            );
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao remover pagamento.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao remover pagamento.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Pagamento findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_pagamento, id_reserva, forma_pagamento, status_pagamento, "
                            + "valor_total, valor_pago, parcelas, data_pagamento "
                            + "FROM pagamento "
                            + "WHERE id_pagamento = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiatePagamento(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar pagamento por id.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Pagamento findByReservaId(Integer idReserva) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_pagamento, id_reserva, forma_pagamento, status_pagamento, "
                            + "valor_total, valor_pago, parcelas, data_pagamento "
                            + "FROM pagamento "
                            + "WHERE id_reserva = ?"
            );
            st.setInt(1, idReserva);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiatePagamento(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar pagamento por reserva.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Pagamento> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_pagamento, id_reserva, forma_pagamento, status_pagamento, "
                            + "valor_total, valor_pago, parcelas, data_pagamento "
                            + "FROM pagamento "
                            + "ORDER BY id_pagamento"
            );
            rs = st.executeQuery();

            List<Pagamento> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiatePagamento(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao listar pagamentos.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Pagamento instantiatePagamento(ResultSet rs) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(rs.getInt("id_reserva"));

        return new Pagamento(
                rs.getInt("id_pagamento"),
                reserva,
                rs.getString("forma_pagamento"),
                rs.getString("status_pagamento"),
                rs.getBigDecimal("valor_total"),
                rs.getBigDecimal("valor_pago"),
                rs.getInt("parcelas"),
                getNullableTimestamp(rs, "data_pagamento")
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
