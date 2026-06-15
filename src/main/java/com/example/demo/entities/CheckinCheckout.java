package com.example.demo.entities;

import java.time.LocalDateTime;

public class CheckinCheckout {

    private Integer idCheckinCheckout;
    private Reserva reserva;
    private LocalDateTime dataCheckinReal;
    private LocalDateTime dataCheckoutReal;
    private String responsavelCheckin;
    private String responsavelCheckout;
    private String statusHospedagem;

    public CheckinCheckout() {
    }

    public CheckinCheckout(Integer idCheckinCheckout, Reserva reserva, LocalDateTime dataCheckinReal,
                           LocalDateTime dataCheckoutReal, String responsavelCheckin,
                           String responsavelCheckout, String statusHospedagem) {
        this.idCheckinCheckout = idCheckinCheckout;
        this.reserva = reserva;
        this.dataCheckinReal = dataCheckinReal;
        this.dataCheckoutReal = dataCheckoutReal;
        this.responsavelCheckin = responsavelCheckin;
        this.responsavelCheckout = responsavelCheckout;
        this.statusHospedagem = statusHospedagem;
    }

    public Integer getIdCheckinCheckout() {
        return idCheckinCheckout;
    }

    public void setIdCheckinCheckout(Integer idCheckinCheckout) {
        this.idCheckinCheckout = idCheckinCheckout;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public LocalDateTime getDataCheckinReal() {
        return dataCheckinReal;
    }

    public void setDataCheckinReal(LocalDateTime dataCheckinReal) {
        this.dataCheckinReal = dataCheckinReal;
    }

    public LocalDateTime getDataCheckoutReal() {
        return dataCheckoutReal;
    }

    public void setDataCheckoutReal(LocalDateTime dataCheckoutReal) {
        this.dataCheckoutReal = dataCheckoutReal;
    }

    public String getResponsavelCheckin() {
        return responsavelCheckin;
    }

    public void setResponsavelCheckin(String responsavelCheckin) {
        this.responsavelCheckin = responsavelCheckin;
    }

    public String getResponsavelCheckout() {
        return responsavelCheckout;
    }

    public void setResponsavelCheckout(String responsavelCheckout) {
        this.responsavelCheckout = responsavelCheckout;
    }

    public String getStatusHospedagem() {
        return statusHospedagem;
    }

    public void setStatusHospedagem(String statusHospedagem) {
        this.statusHospedagem = statusHospedagem;
    }
}
