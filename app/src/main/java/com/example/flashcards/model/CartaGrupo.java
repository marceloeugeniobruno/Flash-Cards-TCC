package com.example.flashcards.model;

public class CartaGrupo {
    private String nome;
    private String frente;
    private String verso;
    private int ordem;
    private String EndWeb;
    private String endLocal;

    public String getFrente() {
        return frente;
    }

    public void setFrente(String frente) {
        this.frente = frente;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }

    public CartaGrupo() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getEndWeb() {
        return EndWeb;
    }

    public void setEndWeb(String endWeb) {
        EndWeb = endWeb;
    }

    public String getEndLocal() {
        return endLocal;
    }

    public void setEndLocal(String endLocal) {
        this.endLocal = endLocal;
    }
}
