package com.example.flashcards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;
import com.example.flashcards.adapter.AdapterPrincipal;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.example.flashcards.model.Baralho;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class BaralhoComumActivity extends AppCompatActivity {
    private String nomeBaralho;
    private DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerBcomun;
    private TextView textoDias;
    private TextView textoCartas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baralho_comum);

    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView textoNome = findViewById(R.id.pbc_tex_nome);
        textoDias = findViewById(R.id.pbc_tex_dias);
        textoCartas = findViewById(R.id.pbc_tex_cartas);
        Bundle dados = getIntent().getExtras();
        nomeBaralho = dados.getString("nomeBaralho");
        textoNome.setText(nomeBaralho);
        pegarValores();
    }

    public void pbcTelaPrincipal(View view){
        finish();
        Intent principal = new Intent(BaralhoComumActivity.this, MainActivity.class);
        startActivity(principal);
    }



    public void pbcIniciarEstudo(View view){
        Intent iniciarEstudo = new Intent(BaralhoComumActivity.this, PerguntaPrimeiraTempoActivity.class);
        iniciarEstudo.putExtra("nomeBaralho", nomeBaralho);
        iniciarEstudo.putExtra("nCartas", 10);
        iniciarEstudo.putExtra("tipo", "Comum");
        startActivity(iniciarEstudo);
    }

    public void pbcAdicinarCarta(View view){
        Intent editorDeCarta = new Intent(BaralhoComumActivity.this, EditorDeCartasActivity.class);
        editorDeCarta.putExtra("nomeBaralho", nomeBaralho);
        editorDeCarta.putExtra("tipo", "Comum");
        startActivity(editorDeCarta);
    }

    public void pbcEditarBaralho(View view){

        Intent editorBaralho = new Intent(BaralhoComumActivity.this, EditorDeBaralhoActivity.class);
        editorBaralho.putExtra("nomeBaralho", nomeBaralho);
        startActivity(editorBaralho);
    }

    public void pbcGerenciarAlarmes(View view){
        Intent gerenciador = new Intent(BaralhoComumActivity.this, GerenciadorDeAlertasActivity.class);
        //TODO: Mandar configurações de alertas e alarmes
        gerenciador.putExtra("alarme", false);
        gerenciador.putExtra("lembrete", false);
        gerenciador.putExtra("hora", 0);
        gerenciador.putExtra("minuto", 0);
        startActivity(gerenciador);
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
        valueEventListenerBcomun = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Baralho baralho = snapshot.getValue(Baralho.class);
                textoDias.setText("Dias de estudo: " + baralho.getDias());
                textoCartas.setText("Cartas Estudadas: " + baralho.getCartas());

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
        usuario.removeEventListener(valueEventListenerBcomun);
    }
}