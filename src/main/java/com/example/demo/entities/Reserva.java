package com.example.demo.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Reserva {

    private Integer idReserva;
    private Hospede hospede;
    private Quarto quarto;
    private LocalDate dataCheckinPrevista;
    private LocalDate dataCheckoutPrevista;
    private String statusReserva;
    private BigDecimal valorTotal;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;

    public Reserva() {
    }

    public Reserva(Integer idReserva, Hospede hospede, Quarto quarto, LocalDate dataCheckinPrevista,
                   LocalDate dataCheckoutPrevista, String statusReserva, BigDecimal valorTotal,
                   LocalDateTime dataCriacao, LocalDateTime dataAtualizacao) {
        this.idReserva = idReserva;
        this.hospede = hospede;
        this.quarto = quarto;
        this.dataCheckinPrevista = dataCheckinPrevista;
        this.dataCheckoutPrevista = dataCheckoutPrevista;
        this.statusReserva = statusReserva;
        this.valorTotal = valorTotal;
        this.dataCriacao = dataCriacao;
        this.dataAtualizacao = dataAtualizacao;
    }

    public Integer getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Integer idReserva) {
        this.idReserva = idReserva;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public LocalDate getDataCheckinPrevista() {
        return dataCheckinPrevista;
    }

    public void setDataCheckinPrevista(LocalDate dataCheckinPrevista) {
        this.dataCheckinPrevista = dataCheckinPrevista;
    }

    public LocalDate getDataCheckoutPrevista() {
        return dataCheckoutPrevista;
    }

    public void setDataCheckoutPrevista(LocalDate dataCheckoutPrevista) {
        this.dataCheckoutPrevista = dataCheckoutPrevista;
    }

    public String getStatusReserva() {
        return statusReserva;
    }

    public void setStatusReserva(String statusReserva) {
        this.statusReserva = statusReserva;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
