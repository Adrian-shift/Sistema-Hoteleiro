package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.db.DbException;
import com.example.demo.db.DbIntegrityException;
import com.example.demo.entities.CheckinCheckout;
import com.example.demo.entities.Reserva;

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

public class CheckinCheckoutDaoJDBC implements CheckinCheckoutDao {

    private final Connection conn;

    public CheckinCheckoutDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(CheckinCheckout checkinCheckout) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO checkin_checkout "
                            + "(id_reserva, data_checkin_real, data_checkout_real, "
                            + "responsavel_checkin, responsavel_checkout, status_hospedagem) "
                            + "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, checkinCheckout.getReserva().getIdReserva());
            setNullableTimestamp(st, 2, checkinCheckout.getDataCheckinReal());
            setNullableTimestamp(st, 3, checkinCheckout.getDataCheckoutReal());
            st.setString(4, checkinCheckout.getResponsavelCheckin());
            st.setString(5, checkinCheckout.getResponsavelCheckout());
            st.setString(6, checkinCheckout.getStatusHospedagem());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Erro inesperado: nenhuma linha inserida em checkin_checkout.");
            }

            ResultSet rs = null;
            try {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    checkinCheckout.setIdCheckinCheckout(rs.getInt(1));
                }
            } finally {
                DB.closeResultSet(rs);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao inserir check-in/check-out.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir check-in/check-out.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(CheckinCheckout checkinCheckout) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE checkin_checkout "
                            + "SET id_reserva = ?, "
                            + "data_checkin_real = ?, "
                            + "data_checkout_real = ?, "
                            + "responsavel_checkin = ?, "
                            + "responsavel_checkout = ?, "
                            + "status_hospedagem = ? "
                            + "WHERE id_checkin_checkout = ?"
            );

            st.setInt(1, checkinCheckout.getReserva().getIdReserva());
            setNullableTimestamp(st, 2, checkinCheckout.getDataCheckinReal());
            setNullableTimestamp(st, 3, checkinCheckout.getDataCheckoutReal());
            st.setString(4, checkinCheckout.getResponsavelCheckin());
            st.setString(5, checkinCheckout.getResponsavelCheckout());
            st.setString(6, checkinCheckout.getStatusHospedagem());
            st.setInt(7, checkinCheckout.getIdCheckinCheckout());

            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao atualizar check-in/check-out.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar check-in/check-out.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM checkin_checkout WHERE id_checkin_checkout = ?"
            );
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao remover check-in/check-out.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao remover check-in/check-out.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public CheckinCheckout findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_checkin_checkout, id_reserva, data_checkin_real, data_checkout_real, "
                            + "responsavel_checkin, responsavel_checkout, status_hospedagem "
                            + "FROM checkin_checkout "
                            + "WHERE id_checkin_checkout = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateCheckinCheckout(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar check-in/check-out por id.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public CheckinCheckout findByReservaId(Integer idReserva) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_checkin_checkout, id_reserva, data_checkin_real, data_checkout_real, "
                            + "responsavel_checkin, responsavel_checkout, status_hospedagem "
                            + "FROM checkin_checkout "
                            + "WHERE id_reserva = ?"
            );
            st.setInt(1, idReserva);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateCheckinCheckout(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar check-in/check-out por reserva.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<CheckinCheckout> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_checkin_checkout, id_reserva, data_checkin_real, data_checkout_real, "
                            + "responsavel_checkin, responsavel_checkout, status_hospedagem "
                            + "FROM checkin_checkout "
                            + "ORDER BY id_checkin_checkout"
            );
            rs = st.executeQuery();

            List<CheckinCheckout> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateCheckinCheckout(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao listar check-ins/check-outs.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private CheckinCheckout instantiateCheckinCheckout(ResultSet rs) throws SQLException {
        Reserva reserva = new Reserva();
        reserva.setIdReserva(rs.getInt("id_reserva"));

        return new CheckinCheckout(
                rs.getInt("id_checkin_checkout"),
                reserva,
                getNullableTimestamp(rs, "data_checkin_real"),
                getNullableTimestamp(rs, "data_checkout_real"),
                rs.getString("responsavel_checkin"),
                rs.getString("responsavel_checkout"),
                rs.getString("status_hospedagem")
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
