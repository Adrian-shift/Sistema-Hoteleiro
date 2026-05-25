package com.example.demo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservaTwo {

    private String nomeHospede;
    private String cpf;
    private String email;
    private String telefone;
    private String numeroQuarto;
    private LocalDate dataCheckIn;
    private LocalDate dataCheckOut;
    private String formaPagamento;
    private String statusPagamento;
    private double valorTotal;

    // Construtor Completo
    public ReservaTwo(String nomeHospede, String cpf, String email, String telefone,
                      String numeroQuarto, LocalDate dataCheckIn, LocalDate dataCheckOut,
                      String formaPagamento, String statusPagamento, double valorTotal) {
        this.nomeHospede = nomeHospede;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.numeroQuarto = numeroQuarto;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.formaPagamento = formaPagamento;
        this.statusPagamento = statusPagamento;
        this.valorTotal = valorTotal;
    }

    // Getters
    public String getNomeHospede() { return nomeHospede; }
    public String getNumeroQuarto() { return numeroQuarto; }
    public LocalDate getDataCheckIn() { return dataCheckIn; }
    public LocalDate getDataCheckOut() { return dataCheckOut; }
    public double getValorTotal() { return valorTotal; }
    public String getTelefone(){return telefone;}
    public String getStatusPagamento(){return statusPagamento;}
    public String getFormaPagamento(){return formaPagamento;}

    public void setValorTotal(double valor) {
        this.valorTotal = valor;
    }
    public void setNumeroQuarto(String numeroQuarto) {
        this.numeroQuarto = numeroQuarto;
    }

    public void setNomeHospede(String nomeHospede) {
        this.nomeHospede = nomeHospede;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    // Método auxiliar para mostrar a data formatada (String)
    public String getDataFormatada() {
        return dataCheckIn.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
