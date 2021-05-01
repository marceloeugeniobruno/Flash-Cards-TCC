package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.flashcards.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


public class PerguntaSegundaAnimoActivity extends AppCompatActivity {

    private String nomeBaralho;
    private String tipo;
    private int nCartas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta_segunda_animo);
        Bundle dados = getIntent().getExtras();

        nomeBaralho = dados.getString("nomeBaralho");
        nCartas = dados.getInt("nCartas");
        tipo = dados.getString("tipo");
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
            proxima = new Intent(PerguntaSegundaAnimoActivity.this, GrupoActivity.class);
        }
        proxima.putExtra("nomeBaralho", nomeBaralho);
        proxima.putExtra("nCartas", nCartas);
        proxima.putExtra("tipo", tipo);
        finish();
        startActivity(proxima);
    }

}