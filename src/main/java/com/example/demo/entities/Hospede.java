package com.example.demo.entities;

import java.time.LocalDateTime;

public class Hospede {

    private Integer idHospede;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String telefone;
    private LocalDateTime dataCadastro;
    private String status;

    public Hospede() {
    }

    public Hospede(Integer idHospede, String nomeCompleto, String cpf, String email, String telefone,
                   LocalDateTime dataCadastro, String status) {
        this.idHospede = idHospede;
        this.nomeCompleto = nomeCompleto;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataCadastro = dataCadastro;
        this.status = status;
    }

    public Integer getIdHospede() {
        return idHospede;
    }

    public void setIdHospede(Integer idHospede) {
        this.idHospede = idHospede;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
