package com.example.demo.dao;

import com.example.demo.entities.Hospede;

import java.util.List;

public interface HospedeDao {

    void insert(Hospede hospede);

    void update(Hospede hospede);

    void deleteById(Integer id);

    Hospede findById(Integer id);

    Hospede findByCpf(String cpf);

    Hospede findByEmail(String email);

    List<Hospede> findByName(String name);

    List<Hospede> findAll();
}
