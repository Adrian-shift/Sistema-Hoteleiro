package com.example.demo.entities;

import java.math.BigDecimal;

public class TipoQuarto {

    private Integer idTipoQuarto;
    private String nome;
    private String descricao;
    private BigDecimal valorDiaria;
    private Integer capacidade;

    public TipoQuarto() {
    }

    public TipoQuarto(Integer idTipoQuarto, String nome, String descricao, BigDecimal valorDiaria,
                      Integer capacidade) {
        this.idTipoQuarto = idTipoQuarto;
        this.nome = nome;
        this.descricao = descricao;
        this.valorDiaria = valorDiaria;
        this.capacidade = capacidade;
    }

    public Integer getIdTipoQuarto() {
        return idTipoQuarto;
    }

    public void setIdTipoQuarto(Integer idTipoQuarto) {
        this.idTipoQuarto = idTipoQuarto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorDiaria() {
        return valorDiaria;
    }

    public void setValorDiaria(BigDecimal valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(Integer capacidade) {
        this.capacidade = capacidade;
    }
}
