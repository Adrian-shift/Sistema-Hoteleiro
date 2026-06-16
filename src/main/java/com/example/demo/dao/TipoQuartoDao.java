package com.example.demo.dao;

import com.example.demo.entities.TipoQuarto;

import java.util.List;

public interface TipoQuartoDao {

    void insert(TipoQuarto tipoQuarto);

    void update(TipoQuarto tipoQuarto);

    void deleteById(Integer id);

    TipoQuarto findById(Integer id);

    TipoQuarto findByNome(String nome);

    List<TipoQuarto> findAll();
}
