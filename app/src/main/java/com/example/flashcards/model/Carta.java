package com.example.flashcards.model;

import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Carta {
    private String identificador;
    private String textoFrente;
    private String textoVerso;
    private String endAudioFrente;
    private String endAudioVerso;
    private String endImagemFrente;
    private String endImagemVerso;
    private String endVideoFrente;
    private String endVideoVerso;
    private int dias;
    private int multiplicador;

    public Carta(String nomeBaralho) {
        /*
        Contrutor utilizado para criação de uma nova carta
         */
        //pega as informações do usuário
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
        String email = Base64Custon.codificarBase64(autenticacao.getCurrentUser().getEmail());
        //cria a carta vazia no firebase
        DatabaseReference cartaRef = ConfiguracaoFirebase.getDatabase()
                .child(email).child(nomeBaralho).child("listaCartas");
        setIdentificador( cartaRef.push().getKey() );
    }
    public Carta (){
        //contrutor é utilizada para ler as cartas no banco de dados
    }


    public void errei (){
        this.dias = 0;
        this.multiplicador = 0;
    }

    public void dificil (){
        this.multiplicador++;
        this.dias = this.dias * multiplicador;
    }

    public void facil (){
        this.multiplicador = this.multiplicador + 2;
        this.dias = this.dias * multiplicador;
    }


    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String id) {
        this.identificador = id;
    }

    public String getTextoFrente() {
        return textoFrente;
    }

    public void setTextoFrente(String textoFrente) {
        this.textoFrente = textoFrente;
    }

    public String getTextoVerso() {
        return textoVerso;
    }

    public void setTextoVerso(String textoVerso) {
        this.textoVerso = textoVerso;
    }

    public String getEndAudioFrente() {
        return endAudioFrente;
    }

    public void setEndAudioFrente(String endAudioFrente) {
        this.endAudioFrente = endAudioFrente;
    }

    public String getEndAudioVerso() {
        return endAudioVerso;
    }

    public void setEndAudioVerso(String endAudioVerso) {
        this.endAudioVerso = endAudioVerso;
    }

    public String getEndImagemFrente() {
        return endImagemFrente;
    }

    public void setEndImagemFrente(String endImagemFrente) {
        this.endImagemFrente = endImagemFrente;
    }

    public String getEndImagemVerso() {
        return endImagemVerso;
    }

    public void setEndImagemVerso(String endImagemVerso) {
        this.endImagemVerso = endImagemVerso;
    }

    public String getEndVideoFrente() {
        return endVideoFrente;
    }

    public void setEndVideoFrente(String endVideoFrente) {
        this.endVideoFrente = endVideoFrente;
    }

    public String getEndVideoVerso() {
        return endVideoVerso;
    }

    public void setEndVideoVerso(String endVideoVerso) {
        this.endVideoVerso = endVideoVerso;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getMultiplicador() {
        return multiplicador;
    }

    public void setMultiplicador(int multiplicador) {
        this.multiplicador = multiplicador;
    }
}
