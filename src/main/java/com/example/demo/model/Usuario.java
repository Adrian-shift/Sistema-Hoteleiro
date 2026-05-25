package com.example.demo.model;

public class Usuario {

    String Nome;
    String email;
    String senha;

    public Usuario(String nome, String email, String senha) {
        Nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
