package com.example.demo.dao;

import com.example.demo.entities.Quarto;

import java.util.List;

public interface QuartoDao {

    void insert(Quarto quarto);

    void update(Quarto quarto);

    void deleteById(Integer id);

    Quarto findById(Integer id);

    Quarto findByNumero(String numero);

    List<Quarto> findByStatus(String status);

    List<Quarto> findAll();
}
