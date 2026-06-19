package com.plataforma.model;

public class UsuarioRanking {
    private int id;
    private String nome;
    private int totalConcluidos;

    public UsuarioRanking(int id, String nome, int totalConcluidos) {
        this.id = id;
        this.nome = nome;
        this.totalConcluidos = totalConcluidos;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public int getTotalConcluidos() { return totalConcluidos; }
}
