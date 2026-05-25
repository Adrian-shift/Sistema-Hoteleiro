package com.example.demo.model;

public class Quarto {
    private int id;
    private int numero;
    private StatusQuarto status;
    private String tipQuarto;

    private double valorDiaria;

    public Quarto(int id, int numero, double valorDiaria) {
        this.id = id;
        this.numero = numero;
        this.valorDiaria = valorDiaria;
        this.status = StatusQuarto.DISPONIVEL;
    }


}

