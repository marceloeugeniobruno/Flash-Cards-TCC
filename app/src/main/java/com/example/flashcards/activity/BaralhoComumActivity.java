package com.example.flashcards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flashcards.R;

public class BaralhoComumActivity extends AppCompatActivity {
    private String nomeBaralho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baralho_comum);


    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView textoNome = findViewById(R.id.pbc_tex_nome);
        TextView textoDias = findViewById(R.id.pbc_tex_dias);
        TextView textoCartas = findViewById(R.id.pbc_tex_cartas);
        Bundle dados = getIntent().getExtras();
        nomeBaralho = dados.getString("nomeBaralho");
        textoNome.setText(nomeBaralho);
        //TODO: criar método para baixar os dados
        textoDias.setText("Dias de estudo: " + 0);
        textoCartas.setText("Cartas Estudadas: " + 0);
    }

    public void pbcTelaPrincipal(View view){
        finish();
        Intent principal = new Intent(BaralhoComumActivity.this, MainActivity.class);
        startActivity(principal);
    }

    public void pbcIniciarEstudo(View view){
        //TODO: CRIAR Tela de add cartas
        Intent iniciarEstudo = new Intent(BaralhoComumActivity.this, PerguntaPrimeiraTempoActivity.class);
        iniciarEstudo.putExtra("nomeBaralho", nomeBaralho);
        iniciarEstudo.putExtra("nCartas", 10);
        iniciarEstudo.putExtra("tipo", "Comum");
        startActivity(iniciarEstudo);
    }

    public void pbcAdicinarCarta(View view){
        //TODO: CRIAR Tela de add cartas
        Intent principal = new Intent(BaralhoComumActivity.this, MainActivity.class);
        startActivity(principal);
    }

    public void pbcEditarBaralho(View view){
        //TODO: CRIAR Tela de editar Baralho
        Intent principal = new Intent(BaralhoComumActivity.this, MainActivity.class);
        startActivity(principal);
    }

    public void pbcGerenciarAlarmes(View view){
        //TODO: CRIAR Tela de gerenciamento de alarmes
        Intent principal = new Intent(BaralhoComumActivity.this, MainActivity.class);
        startActivity(principal);
    }


}