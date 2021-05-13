package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.flashcards.R;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.example.flashcards.model.Baralho;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class BaralhoIdiomasActivity extends AppCompatActivity {

    private String nomeBaralho;
    private DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerBidiomas;
    private TextView textoDias;
    private TextView textoCartas;
    private TextView textoTextos;
    private TextView textoPalavrasUni;


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
        editorDeCarta.putExtra("flag", 1);
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

    @Override
    protected void onStart() {
        super.onStart();
        TextView textoNome = findViewById(R.id.pbi_txt_nomeBar);
        textoDias = findViewById(R.id.pbi_tex_dias);
        textoCartas = findViewById(R.id.pbi_tex_cartas);
        textoTextos = findViewById(R.id.pbi_tex_textos);
        textoPalavrasUni = findViewById(R.id.pbi_tex_palavras);
        Bundle dados = getIntent().getExtras();
        nomeBaralho = dados.getString("nomeBaralho");
        textoNome.setText(nomeBaralho);
        pegarValores();
    }

    public void pegarValores(){
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
        String email = "";
        if(autenticacao.getCurrentUser() == null){
            //TODO: Fazer tratamento de erro
        }else{
            email = Base64Custon.codificarBase64(autenticacao.getCurrentUser().getEmail());
        }
        usuario = database.child(email).child(nomeBaralho);
        valueEventListenerBidiomas = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Baralho baralho = snapshot.getValue(Baralho.class);
                textoDias.setText("Dias de estudo: " + baralho.getDias());
                textoCartas.setText("Cartas Estudadas: " + baralho.getCartas());
                textoTextos.setText("Textos Estudados: " + baralho.getTextos());
                textoPalavrasUni.setText("Palavras Unicas Visualizadas: " + baralho.getPalavrasUnicas());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO: Fazer tratamento de errro
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        usuario.removeEventListener(valueEventListenerBidiomas);
    }


}