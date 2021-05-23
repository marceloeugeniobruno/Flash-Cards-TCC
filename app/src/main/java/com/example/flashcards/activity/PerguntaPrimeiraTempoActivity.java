package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.flashcards.R;

public class PerguntaPrimeiraTempoActivity extends AppCompatActivity {
    private int nCartas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta_primeira_tempo);
        Bundle dados = getIntent().getExtras();
        nCartas = dados.getInt("nCartas");
    }

    public void comTempo(View view){
        nCartas = nCartas + 10;
        encaminhar();
    }

    public void tenhoTempo(View view){
        nCartas = nCartas + 5;
        encaminhar();
    }
    public void toOcupado(View view){
        encaminhar();
    }

    public void encaminhar(){

        Intent proxima = new Intent(PerguntaPrimeiraTempoActivity.this, PerguntaSegundaAnimoActivity.class);
        proxima.putExtra("nCartas", nCartas);
        finish();
        startActivity(proxima);

    }

}