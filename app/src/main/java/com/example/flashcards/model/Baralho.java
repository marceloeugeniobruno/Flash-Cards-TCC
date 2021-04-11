package com.example.flashcards.model;

import java.util.List;

public class Baralho {
    String id;
    List<Carta> listaCartas;

    public Baralho() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Carta> getListaCartas() {
        return listaCartas;
    }

    public void addListaCartas(Carta carta) {
        this.listaCartas.add(carta);
    }

    public void excluirCartas(int pocicao){
        this.listaCartas.remove(pocicao);
    }
}
