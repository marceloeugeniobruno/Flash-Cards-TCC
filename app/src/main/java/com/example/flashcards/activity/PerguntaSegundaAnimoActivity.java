package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.flashcards.R;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;


public class PerguntaSegundaAnimoActivity extends AppCompatActivity {

    private String tipo;
    private int nCartas;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta_segunda_animo);
        Bundle dados = getIntent().getExtras();
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
        nCartas = dados.getInt("nCartas");
        tipo = preferences.getString("tipoBaralho", "");
    }
    public void animado(View view){
        if(tipo.equals("Comum")){
            nCartas = nCartas + 30;
        }else{
            nCartas = nCartas + 15;
        }
        encaminharSPU();
    }

    public void tranquilo(View view){
        if(tipo.equals("Comum")){
            nCartas = nCartas + 15;
        }else{
            nCartas = nCartas + 5;
        }
        encaminharSPU();
    }
    public void desanimado(View view){
        encaminharSPU();
    }

    public void encaminharSPU(){
        Intent proxima;
        if(tipo.equals("Comum")){
            proxima = new Intent(PerguntaSegundaAnimoActivity.this, CartaActivity.class);
        }else{
            proxima = new Intent(PerguntaSegundaAnimoActivity.this, TextoeAudiosActivity.class);
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("nCartas", nCartas);
        editor.apply();
        finish();
        startActivity(proxima);
    }

}