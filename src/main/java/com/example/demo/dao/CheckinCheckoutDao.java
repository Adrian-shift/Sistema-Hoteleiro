package com.example.demo.dao;

import com.example.demo.entities.CheckinCheckout;

import java.util.List;

public interface CheckinCheckoutDao {

    void insert(CheckinCheckout checkinCheckout);

    void update(CheckinCheckout checkinCheckout);

    void deleteById(Integer id);

    CheckinCheckout findById(Integer id);

    CheckinCheckout findByReservaId(Integer idReserva);

    List<CheckinCheckout> findAll();
}
