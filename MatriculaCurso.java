package com.plataforma.model;

public class MatriculaCurso {
    private int id;
    private String titulo;
    private String descricao;
    private int cargaHoraria;
    private boolean concluido;

    public MatriculaCurso(int id, String titulo, String descricao, int cargaHoraria, boolean concluido) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.cargaHoraria = cargaHoraria;
        this.concluido = concluido;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescricao() { return descricao; }
    public int getCargaHoraria() { return cargaHoraria; }
    public boolean isConcluido() { return concluido; }
    public String getStatus() { return concluido ? "Concluído ✅" : "Em andamento"; }
}
