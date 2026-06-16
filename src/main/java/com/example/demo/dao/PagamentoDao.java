package com.example.demo.dao;

import com.example.demo.entities.Pagamento;

import java.util.List;

public interface PagamentoDao {

    void insert(Pagamento pagamento);

    void update(Pagamento pagamento);

    void deleteById(Integer id);

    Pagamento findById(Integer id);

    Pagamento findByReservaId(Integer idReserva);

    List<Pagamento> findAll();
}
