package com.example.flashcards.model;

public class Face {
    String id, texto, enderecoSom, enderecoImagem;

    public Face() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getEnderecoSom() {
        return enderecoSom;
    }

    public void setEnderecoSom(String enderecoSom) {
        this.enderecoSom = enderecoSom;
    }

    public String getEnderecoImagem() {
        return enderecoImagem;
    }

    public void setEnderecoImagem(String enderecoImagem) {
        this.enderecoImagem = enderecoImagem;
    }
}
