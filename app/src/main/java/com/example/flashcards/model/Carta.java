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
    private String nomeBaralho;
    private int dias;
    private int diaA;
    private int multiplicador;
    private String endAudioFrenteWeb;
    private String endAudioVersoWeb;
    private int ordem;

    public String getEndAudioFrenteWeb() {
        return endAudioFrenteWeb;
    }

    public void setEndAudioFrenteWeb(String endAudioFrenteWeb) {
        this.endAudioFrenteWeb = endAudioFrenteWeb;
    }

    public String getEndAudioVersoWeb() {
        return endAudioVersoWeb;
    }

    public void setEndAudioVersoWeb(String endAudioVersoWeb) {
        this.endAudioVersoWeb = endAudioVersoWeb;
    }


    public String getNomeBaralho() {
        return nomeBaralho;
    }

    public void setNomeBaralho(String nomeBaralho) {
        this.nomeBaralho = nomeBaralho;
    }

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
        errei();
    }
    public Carta (){
        //contrutor é utilizada para ler as cartas no banco de dados
    }


    public void errei (){
        this.dias = 0;
        this.multiplicador = 0;
        this.diaA = 0;

    }

    public void dificil (){
        this.multiplicador++;
        this.diaA = this.diaA * multiplicador;
        if(diaA == 0){
            diaA = 1;
        }
        this.dias = diaA;
    }

    public void facil (){
        this.multiplicador = this.multiplicador + 2;
        this.diaA = this.diaA * multiplicador;
        if(diaA == 0){
            diaA = 2;
        }
        this.dias = diaA;
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

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public int getDiaA() {
        return diaA;
    }
}
