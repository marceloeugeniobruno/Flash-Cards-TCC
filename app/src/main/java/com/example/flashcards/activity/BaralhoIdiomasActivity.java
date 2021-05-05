package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.flashcards.R;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class BaralhoIdiomasActivity extends AppCompatActivity {

    private String nomeBaralho;
    private DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerBidiomas;
    private TextView textoDias;
    private TextView textoCartas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baralho_idiomas);
    }

    public void pbiTelaPrincipal(View view){
        finish();
        Intent principal = new Intent(BaralhoIdiomasActivity.this, MainActivity.class);
        startActivity(principal);
    }

    public void pbiIniciarEstudo(View view){
        Intent iniciarEstudo = new Intent(BaralhoIdiomasActivity.this, PerguntaPrimeiraTempoActivity.class);
        iniciarEstudo.putExtra("nomeBaralho", nomeBaralho);
        iniciarEstudo.putExtra("nCartas", 0);
        iniciarEstudo.putExtra("tipo", "Idiomas");
        startActivity(iniciarEstudo);
    }

    public void pbiAdicinarCarta(View view){
        Intent editorDeCarta = new Intent(BaralhoIdiomasActivity.this, EditorDeCartasActivity.class);
        editorDeCarta.putExtra("nomeBaralho", nomeBaralho);
        editorDeCarta.putExtra("tipo", "Idiomas");
        startActivity(editorDeCarta);
    }

    public void pbiGerenciarAlarmes(View view){
        Intent gerenciador = new Intent(BaralhoIdiomasActivity.this, GerenciadorDeAlertasActivity.class);
        //TODO: Mandar configurações de alertas e alarmes
        gerenciador.putExtra("alarme", false);
        gerenciador.putExtra("lembrete", false);
        gerenciador.putExtra("hora", 0);
        gerenciador.putExtra("minuto", 0);
        startActivity(gerenciador);
    }

    public void pbiEditarGrupo(View view){
        Intent editorDeGrupo = new Intent(BaralhoIdiomasActivity.this, EditorDeGruposActivity.class);
        editorDeGrupo.putExtra("nomeBaralho", nomeBaralho);
        startActivity(editorDeGrupo);
    }

    public void pbiEditarBaralhoIdiomas(View view){
        Intent editorDeBaralho = new Intent(BaralhoIdiomasActivity.this, EditorDeBaralhoIdiomasActivity.class);
        editorDeBaralho.putExtra("nomeBaralho", nomeBaralho);
        startActivity(editorDeBaralho);
    }

}