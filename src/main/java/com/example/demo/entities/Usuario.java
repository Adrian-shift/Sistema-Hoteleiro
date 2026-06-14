package com.example.demo.entities;

import java.time.LocalDateTime;

public class Usuario {

    private Integer idUsuario;
    private String nome;
    private String email;
    private String senhaHash;
    private Boolean ativo;
    private LocalDateTime dataCadastro;

    public Usuario() {
    }

    public Usuario(Integer idUsuario, String nome, String email, String senhaHash, Boolean ativo,
                   LocalDateTime dataCadastro) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.email = email;
        this.senhaHash = senhaHash;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenhaHash() {
        return senhaHash;
    }

    public void setSenhaHash(String senhaHash) {
        this.senhaHash = senhaHash;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDateTime dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
