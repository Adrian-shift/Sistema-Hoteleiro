package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.db.DbException;
import com.example.demo.db.DbIntegrityException;
import com.example.demo.entities.Quarto;
import com.example.demo.entities.TipoQuarto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QuartoDaoJDBC implements QuartoDao {

    private final Connection conn;

    public QuartoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Quarto quarto) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO quarto "
                            + "(id_tipo_quarto, numero, status_ocupacao, ativo) "
                            + "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setInt(1, quarto.getTipoQuarto().getIdTipoQuarto());
            st.setString(2, quarto.getNumero());
            st.setString(3, quarto.getStatusOcupacao());
            st.setBoolean(4, quarto.getAtivo());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Erro inesperado: nenhuma linha inserida em quarto.");
            }

            ResultSet rs = null;
            try {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    quarto.setIdQuarto(rs.getInt(1));
                }
            } finally {
                DB.closeResultSet(rs);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao inserir quarto.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir quarto.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Quarto quarto) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE quarto "
                            + "SET id_tipo_quarto = ?, "
                            + "numero = ?, "
                            + "status_ocupacao = ?, "
                            + "ativo = ? "
                            + "WHERE id_quarto = ?"
            );

            st.setInt(1, quarto.getTipoQuarto().getIdTipoQuarto());
            st.setString(2, quarto.getNumero());
            st.setString(3, quarto.getStatusOcupacao());
            st.setBoolean(4, quarto.getAtivo());
            st.setInt(5, quarto.getIdQuarto());

            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao atualizar quarto.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar quarto.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM quarto WHERE id_quarto = ?"
            );
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao remover quarto.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao remover quarto.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Quarto findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_quarto, id_tipo_quarto, numero, status_ocupacao, ativo "
                            + "FROM quarto "
                            + "WHERE id_quarto = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateQuarto(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar quarto por id.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Quarto findByNumero(String numero) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_quarto, id_tipo_quarto, numero, status_ocupacao, ativo "
                            + "FROM quarto "
                            + "WHERE numero = ?"
            );
            st.setString(1, numero);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateQuarto(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar quarto por numero.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Quarto> findByStatus(String status) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_quarto, id_tipo_quarto, numero, status_ocupacao, ativo "
                            + "FROM quarto "
                            + "WHERE status_ocupacao = ? "
                            + "ORDER BY numero"
            );
            st.setString(1, status);
            rs = st.executeQuery();

            List<Quarto> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateQuarto(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar quartos por status.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Quarto> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_quarto, id_tipo_quarto, numero, status_ocupacao, ativo "
                            + "FROM quarto "
                            + "ORDER BY numero"
            );
            rs = st.executeQuery();

            List<Quarto> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateQuarto(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao listar quartos.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Quarto instantiateQuarto(ResultSet rs) throws SQLException {
        TipoQuarto tipoQuarto = new TipoQuarto();
        tipoQuarto.setIdTipoQuarto(rs.getInt("id_tipo_quarto"));

        return new Quarto(
                rs.getInt("id_quarto"),
                tipoQuarto,
                rs.getString("numero"),
                rs.getString("status_ocupacao"),
                rs.getBoolean("ativo")
        );
    }
}
