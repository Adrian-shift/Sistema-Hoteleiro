package com.example.demo.db;

public class DbIntegrityExeption extends RuntimeException{
    private static final long serialVersionUID = 1L;
    public DbIntegrityExeption(String msg){

        super(msg);
    }

}
