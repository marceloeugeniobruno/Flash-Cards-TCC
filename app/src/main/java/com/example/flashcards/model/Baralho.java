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

    private List<Carta> listaDeCartas;

    public Baralho() {
        listaDeCartas = new List<Carta>() {
            @Override
            public int size() {
                return 0;
            }
            @Override
            public boolean isEmpty() {
                return false;
            }
            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<Carta> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] a) {
                return null;
            }

            @Override
            public boolean add(Carta carta) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends Carta> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, @NonNull Collection<? extends Carta> c) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Carta get(int index) {
                return null;
            }

            @Override
            public Carta set(int index, Carta element) {
                return null;
            }

            @Override
            public void add(int index, Carta element) {

            }

            @Override
            public Carta remove(int index) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<Carta> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<Carta> listIterator(int index) {
                return null;
            }

            @NonNull
            @Override
            public List<Carta> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
    }

    public void addCarta (Carta carta){
        listaDeCartas.add(carta);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Carta> returnaListaCarta(){
        return listaDeCartas;
    }

    public boolean isAlarme() {return alarme;}

    public void setAlarme(boolean alarme) {this.alarme = alarme;}

    public boolean isLembrete() {return lembrete;}

    public void setLembrete(boolean lembrete) {this.lembrete = lembrete;}

    public int getHora() {return hora;}

    public void setHora(int hora) {this.hora = hora;}

    public int getMinuto() {return minuto;}

    public void setMinuto(int minuto) {this.minuto = minuto;}

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}


}
