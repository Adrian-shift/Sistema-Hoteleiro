package com.example.demo.entities;

public class Quarto {

    private Integer idQuarto;
    private TipoQuarto tipoQuarto;
    private String numero;
    private String statusOcupacao;
    private Boolean ativo;

    public Quarto() {
    }

    public Quarto(Integer idQuarto, TipoQuarto tipoQuarto, String numero, String statusOcupacao, Boolean ativo) {
        this.idQuarto = idQuarto;
        this.tipoQuarto = tipoQuarto;
        this.numero = numero;
        this.statusOcupacao = statusOcupacao;
        this.ativo = ativo;
    }

    public Integer getIdQuarto() {
        return idQuarto;
    }

    public void setIdQuarto(Integer idQuarto) {
        this.idQuarto = idQuarto;
    }

    public TipoQuarto getTipoQuarto() {
        return tipoQuarto;
    }

    public void setTipoQuarto(TipoQuarto tipoQuarto) {
        this.tipoQuarto = tipoQuarto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getStatusOcupacao() {
        return statusOcupacao;
    }

    public void setStatusOcupacao(String statusOcupacao) {
        this.statusOcupacao = statusOcupacao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
}
