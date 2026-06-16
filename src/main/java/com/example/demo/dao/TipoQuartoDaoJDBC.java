package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.db.DbException;
import com.example.demo.db.DbIntegrityException;
import com.example.demo.entities.TipoQuarto;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TipoQuartoDaoJDBC implements TipoQuartoDao {

    private final Connection conn;

    public TipoQuartoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(TipoQuarto tipoQuarto) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO tipo_quarto "
                            + "(nome, descricao, valor_diaria, capacidade) "
                            + "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, tipoQuarto.getNome());
            st.setString(2, tipoQuarto.getDescricao());
            st.setBigDecimal(3, tipoQuarto.getValorDiaria());
            st.setInt(4, tipoQuarto.getCapacidade());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Erro inesperado: nenhuma linha inserida em tipo_quarto.");
            }

            ResultSet rs = null;
            try {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    tipoQuarto.setIdTipoQuarto(rs.getInt(1));
                }
            } finally {
                DB.closeResultSet(rs);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao inserir tipo de quarto.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir tipo de quarto.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(TipoQuarto tipoQuarto) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE tipo_quarto "
                            + "SET nome = ?, "
                            + "descricao = ?, "
                            + "valor_diaria = ?, "
                            + "capacidade = ? "
                            + "WHERE id_tipo_quarto = ?"
            );

            st.setString(1, tipoQuarto.getNome());
            st.setString(2, tipoQuarto.getDescricao());
            st.setBigDecimal(3, tipoQuarto.getValorDiaria());
            st.setInt(4, tipoQuarto.getCapacidade());
            st.setInt(5, tipoQuarto.getIdTipoQuarto());

            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao atualizar tipo de quarto.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar tipo de quarto.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM tipo_quarto WHERE id_tipo_quarto = ?"
            );
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao remover tipo de quarto.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao remover tipo de quarto.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public TipoQuarto findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_tipo_quarto, nome, descricao, valor_diaria, capacidade "
                            + "FROM tipo_quarto "
                            + "WHERE id_tipo_quarto = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateTipoQuarto(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar tipo de quarto por id.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public TipoQuarto findByNome(String nome) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_tipo_quarto, nome, descricao, valor_diaria, capacidade "
                            + "FROM tipo_quarto "
                            + "WHERE nome = ?"
            );
            st.setString(1, nome);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateTipoQuarto(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar tipo de quarto por nome.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<TipoQuarto> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_tipo_quarto, nome, descricao, valor_diaria, capacidade "
                            + "FROM tipo_quarto "
                            + "ORDER BY nome"
            );
            rs = st.executeQuery();

            List<TipoQuarto> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateTipoQuarto(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao listar tipos de quarto.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private TipoQuarto instantiateTipoQuarto(ResultSet rs) throws SQLException {
        return new TipoQuarto(
                rs.getInt("id_tipo_quarto"),
                rs.getString("nome"),
                rs.getString("descricao"),
                rs.getBigDecimal("valor_diaria"),
                rs.getInt("capacidade")
        );
    }
}
