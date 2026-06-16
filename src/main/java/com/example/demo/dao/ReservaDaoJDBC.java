package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.db.DbException;
import com.example.demo.db.DbIntegrityException;
import com.example.demo.entities.Hospede;
import com.example.demo.entities.Quarto;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservaDaoJDBC implements ReservaDao {

    private final Connection conn;

    public ReservaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Reserva reserva) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO reserva "
                            + "(id_hospede, id_quarto, data_checkin_prevista, data_checkout_prevista, "
                            + "status_reserva, valor_total) "
                            + "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, reserva.getHospede().getIdHospede());
            st.setInt(2, reserva.getQuarto().getIdQuarto());
            st.setDate(3, toSqlDate(reserva.getDataCheckinPrevista()));
            st.setDate(4, toSqlDate(reserva.getDataCheckoutPrevista()));

            st.setString(
                    5,
                    reserva.getStatusReserva() != null
                            ? reserva.getStatusReserva()
                            : "CRIADA"
            );

            st.setBigDecimal(6, reserva.getValorTotal());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Erro inesperado: nenhuma linha inserida em reserva.");
            }

            ResultSet rs = null;
            try {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    reserva.setIdReserva(rs.getInt(1));
                }
            } finally {
                DB.closeResultSet(rs);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao inserir reserva.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir reserva.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Reserva reserva) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE reserva "
                            + "SET id_hospede = ?, "
                            + "id_quarto = ?, "
                            + "data_checkin_prevista = ?, "
                            + "data_checkout_prevista = ?, "
                            + "status_reserva = ?, "
                            + "valor_total = ?, "
                            + "data_atualizacao = ? "
                            + "WHERE id_reserva = ?"
            );

            st.setInt(1, reserva.getHospede().getIdHospede());
            st.setInt(2, reserva.getQuarto().getIdQuarto());
            st.setDate(3, toSqlDate(reserva.getDataCheckinPrevista()));
            st.setDate(4, toSqlDate(reserva.getDataCheckoutPrevista()));
            st.setString(5, reserva.getStatusReserva());
            st.setBigDecimal(6, reserva.getValorTotal());
            setNullableTimestamp(st, 7, reserva.getDataAtualizacao());
            st.setInt(8, reserva.getIdReserva());

            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao atualizar reserva.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar reserva.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM reserva WHERE id_reserva = ?"
            );
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao remover reserva.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao remover reserva.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Reserva findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_reserva, id_hospede, id_quarto, data_checkin_prevista, data_checkout_prevista, "
                            + "status_reserva, valor_total, data_criacao, data_atualizacao "
                            + "FROM reserva "
                            + "WHERE id_reserva = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateReserva(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar reserva por id.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Reserva> findByHospedeId(Integer idHospede) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_reserva, id_hospede, id_quarto, data_checkin_prevista, data_checkout_prevista, "
                            + "status_reserva, valor_total, data_criacao, data_atualizacao "
                            + "FROM reserva "
                            + "WHERE id_hospede = ? "
                            + "ORDER BY data_checkin_prevista DESC"
            );
            st.setInt(1, idHospede);
            rs = st.executeQuery();

            List<Reserva> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateReserva(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar reservas por hospede.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Reserva> findByQuartoId(Integer idQuarto) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_reserva, id_hospede, id_quarto, data_checkin_prevista, data_checkout_prevista, "
                            + "status_reserva, valor_total, data_criacao, data_atualizacao "
                            + "FROM reserva "
                            + "WHERE id_quarto = ? "
                            + "ORDER BY data_checkin_prevista DESC"
            );
            st.setInt(1, idQuarto);
            rs = st.executeQuery();

            List<Reserva> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateReserva(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar reservas por quarto.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Reserva> findByStatus(String status) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_reserva, id_hospede, id_quarto, data_checkin_prevista, data_checkout_prevista, "
                            + "status_reserva, valor_total, data_criacao, data_atualizacao "
                            + "FROM reserva "
                            + "WHERE status_reserva = ? "
                            + "ORDER BY data_checkin_prevista DESC"
            );
            st.setString(1, status);
            rs = st.executeQuery();

            List<Reserva> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateReserva(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar reservas por status.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Reserva> findByDateRange(LocalDate dataInicio, LocalDate dataFim) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_reserva, id_hospede, id_quarto, data_checkin_prevista, data_checkout_prevista, "
                            + "status_reserva, valor_total, data_criacao, data_atualizacao "
                            + "FROM reserva "
                            + "WHERE data_checkin_prevista >= ? AND data_checkin_prevista <= ? "
                            + "ORDER BY data_checkin_prevista"
            );
            st.setDate(1, toSqlDate(dataInicio));
            st.setDate(2, toSqlDate(dataFim));
            rs = st.executeQuery();

            List<Reserva> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateReserva(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar reservas por periodo.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Reserva> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_reserva, id_hospede, id_quarto, data_checkin_prevista, data_checkout_prevista, "
                            + "status_reserva, valor_total, data_criacao, data_atualizacao "
                            + "FROM reserva "
                            + "ORDER BY data_checkin_prevista DESC"
            );
            rs = st.executeQuery();

            List<Reserva> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateReserva(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao listar reservas.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Reserva instantiateReserva(ResultSet rs) throws SQLException {
        Hospede hospede = new Hospede();
        hospede.setIdHospede(rs.getInt("id_hospede"));

        Quarto quarto = new Quarto();
        quarto.setIdQuarto(rs.getInt("id_quarto"));

        return new Reserva(
                rs.getInt("id_reserva"),
                hospede,
                quarto,
                toLocalDate(rs.getDate("data_checkin_prevista")),
                toLocalDate(rs.getDate("data_checkout_prevista")),
                rs.getString("status_reserva"),
                rs.getBigDecimal("valor_total"),
                getNullableTimestamp(rs, "data_criacao"),
                getNullableTimestamp(rs, "data_atualizacao")
        );
    }

    private static java.sql.Date toSqlDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return java.sql.Date.valueOf(localDate);
    }

    private static LocalDate toLocalDate(java.sql.Date sqlDate) {
        if (sqlDate == null) {
            return null;
        }
        return sqlDate.toLocalDate();
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
