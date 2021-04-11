package com.example.flashcards.model;

public class Carta {
    String id;
    Face frente, verso;

    public Carta() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Face getFrente() {
        return frente;
    }

    public void setFrente(Face frente) {
        this.frente = frente;
    }

    public Face getVerso() {
        return verso;
    }

    public void setVerso(Face verso) {
        this.verso = verso;
    }
}
