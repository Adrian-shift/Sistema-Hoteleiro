package com.example.demo.dao;

import com.example.demo.entities.Usuario;

import java.util.List;

public interface UsuarioDao {

    void insert(Usuario usuario);

    void update(Usuario usuario);

    void deleteById(Integer id);

    Usuario findById(Integer id);

    Usuario findByEmail(String email);

    boolean validarLogin(String email, String senhaHash);

    List<Usuario> findAll();
}


