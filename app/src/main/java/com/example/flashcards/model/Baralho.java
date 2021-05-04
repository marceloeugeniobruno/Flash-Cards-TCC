package com.example.flashcards.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Baralho {
    private String tipo;
    private String nome;
    private boolean alarme ;
    private boolean lembrete;
    private int hora;
    private int minuto;
    private int dias;
    private int cartas;

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getCartas() {
        return cartas;
    }

    public void setCartas(int cartas) {
        this.cartas = cartas;
    }

    public int getTextos() {
        return textos;
    }

    public void setTextos(int textos) {
        this.textos = textos;
    }

    public int getPalavrasUnicas() {
        return palavrasUnicas;
    }

    public void setPalavrasUnicas(int palavrasUnicas) {
        this.palavrasUnicas = palavrasUnicas;
    }

    private int textos;
    private int palavrasUnicas;


    public Baralho() {
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}


    public boolean isAlarme() {return alarme;}

    public void setAlarme(boolean alarme) {this.alarme = alarme;}

    public boolean isLembrete() {return lembrete;}

    public void setLembrete(boolean lembrete) {this.lembrete = lembrete;}

    public int getHora() {return hora;}

    public void setHora(int hora) {this.hora = hora;}

    public int getMinuto() {return minuto;}

    public void setMinuto(int minuto) {this.minuto = minuto;}




}
