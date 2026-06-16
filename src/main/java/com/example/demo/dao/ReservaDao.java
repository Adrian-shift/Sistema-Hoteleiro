package com.example.demo.dao;

import com.example.demo.entities.Reserva;

import java.time.LocalDate;
import java.util.List;

public interface ReservaDao {

    void insert(Reserva reserva);

    void update(Reserva reserva);

    void deleteById(Integer id);

    Reserva findById(Integer id);

    List<Reserva> findByHospedeId(Integer idHospede);

    List<Reserva> findByQuartoId(Integer idQuarto);

    List<Reserva> findByStatus(String status);

    List<Reserva> findByDateRange(LocalDate dataInicio, LocalDate dataFim);

    List<Reserva> findAll();
}
