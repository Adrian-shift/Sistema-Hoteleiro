package com.example.demo.dao;

import com.example.demo.db.DB;
import com.example.demo.db.DbException;
import com.example.demo.db.DbIntegrityException;
import com.example.demo.entities.Usuario;

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

public class UsuarioDaoJDBC implements UsuarioDao {

    private final Connection conn;

    public UsuarioDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Usuario usuario) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "INSERT INTO usuario "
                            + "(nome, email, senha_hash, ativo) "
                            + "VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            st.setString(1, usuario.getNome());
            st.setString(2, usuario.getEmail());
            st.setString(3, usuario.getSenhaHash());
            st.setBoolean(4, usuario.getAtivo() != null ? usuario.getAtivo() : true);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected == 0) {
                throw new DbException("Erro inesperado: nenhuma linha inserida em usuario.");
            }

            ResultSet rs = null;
            try {
                rs = st.getGeneratedKeys();
                if (rs.next()) {
                    usuario.setIdUsuario(rs.getInt(1));
                }
            } finally {
                DB.closeResultSet(rs);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao inserir usuario (email ja existe).", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir usuario.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Usuario usuario) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "UPDATE usuario "
                            + "SET nome = ?, "
                            + "email = ?, "
                            + "senha_hash = ?, "
                            + "ativo = ?, "
                            + "data_cadastro = ? "
                            + "WHERE id_usuario = ?"
            );

            st.setString(1, usuario.getNome());
            st.setString(2, usuario.getEmail());
            st.setString(3, usuario.getSenhaHash());
            st.setBoolean(4, usuario.getAtivo() != null ? usuario.getAtivo() : true);
            setNullableTimestamp(st, 5, usuario.getDataCadastro());
            st.setInt(6, usuario.getIdUsuario());

            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao atualizar usuario (email ja existe).", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar usuario.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement(
                    "DELETE FROM usuario WHERE id_usuario = ?"
            );
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DbIntegrityException("Violacao de integridade ao remover usuario.", e);
        } catch (SQLException e) {
            throw new DbException("Erro ao remover usuario.", e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Usuario findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_usuario, nome, email, senha_hash, ativo, data_cadastro "
                            + "FROM usuario "
                            + "WHERE id_usuario = ?"
            );
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateUsuario(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar usuario por id.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public Usuario findByEmail(String email) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_usuario, nome, email, senha_hash, ativo, data_cadastro "
                            + "FROM usuario "
                            + "WHERE email = ?"
            );
            st.setString(1, email);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateUsuario(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar usuario por e-mail.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public boolean validarLogin(String email, String senhaHash) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_usuario, nome, email, senha_hash, ativo, data_cadastro "
                            + "FROM usuario "
                            + "WHERE email = ? AND senha_hash = ? AND ativo = true"
            );
            st.setString(1, email);
            st.setString(2, senhaHash);
            rs = st.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new DbException("Erro ao validar login.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Usuario> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT id_usuario, nome, email, senha_hash, ativo, data_cadastro "
                            + "FROM usuario "
                            + "ORDER BY nome"
            );
            rs = st.executeQuery();

            List<Usuario> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateUsuario(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DbException("Erro ao listar usuarios.", e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    private Usuario instantiateUsuario(ResultSet rs) throws SQLException {
        return new Usuario(
                rs.getInt("id_usuario"),
                rs.getString("nome"),
                rs.getString("email"),
                rs.getString("senha_hash"),
                rs.getBoolean("ativo"),
                getNullableTimestamp(rs, "data_cadastro")
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
