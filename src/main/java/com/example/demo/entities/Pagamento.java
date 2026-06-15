package com.example.demo.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Pagamento {

    private Integer idPagamento;
    private Reserva reserva;
    private String formaPagamento;
    private String statusPagamento;
    private BigDecimal valorTotal;
    private BigDecimal valorPago;
    private Integer parcelas;
    private LocalDateTime dataPagamento;

    public Pagamento() {
    }

    public Pagamento(Integer idPagamento, Reserva reserva, String formaPagamento, String statusPagamento,
                     BigDecimal valorTotal, BigDecimal valorPago, Integer parcelas, LocalDateTime dataPagamento) {
        this.idPagamento = idPagamento;
        this.reserva = reserva;
        this.formaPagamento = formaPagamento;
        this.statusPagamento = statusPagamento;
        this.valorTotal = valorTotal;
        this.valorPago = valorPago;
        this.parcelas = parcelas;
        this.dataPagamento = dataPagamento;
    }

    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
