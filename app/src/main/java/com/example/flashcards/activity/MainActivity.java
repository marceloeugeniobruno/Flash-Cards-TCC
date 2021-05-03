package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected final static String ARQUIVO_PREFERENCIAS = "arquivoPreferencia";
    private String email;
    private DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerUsuario;
    private List<Baralho> listaDeBaralhos = new ArrayList<>();
    private AdapterPrincipal adapterPrincipal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
        RecyclerView recyclerView = findViewById(R.id.pri_rc);

        //criação da lista
        usuario = database.child(email);
        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //TODO: tratar os dados para fazer a lista
                listaDeBaralhos.clear();
                for(DataSnapshot dados: snapshot.getChildren()){

                    Baralho baralho = dados.getValue(Baralho.class);
                    listaDeBaralhos.add(baralho);
                }
                adapterPrincipal.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //configuração do adapter
        //TODO: alterar o adapter para receber uma lista
        adapterPrincipal = new AdapterPrincipal(listaDeBaralhos, this);
        //configuração da Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterPrincipal);
    }

    public void verificarUsuarioLogado(){
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
        if(autenticacao.getCurrentUser() == null){
            Intent inicial = new Intent(getApplicationContext(), InicialActivity.class);
            startActivity(inicial);
        }else{
            email = Base64Custon.codificarBase64(autenticacao.getCurrentUser().getEmail());
        }

    }

    public void criarcaodeBaralho(View view){
        Intent inicial = new Intent(getApplicationContext(), CriarBaralhoActivity.class);
        startActivity(inicial);
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuario.removeEventListener(valueEventListenerUsuario);


    }
}