package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;
import com.example.flashcards.adapter.AdapterEditorGrupo;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.model.CartaGrupo;
import com.example.flashcards.model.Grupo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class EditorDeGruposActivity extends AppCompatActivity {
    private String nomeConjunto;
    private String nomeGrupo;
    private SharedPreferences preferences;
    private final DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerUsuario;
    private SharedPreferences.Editor editor;
    private final List<CartaGrupo> listaDeCarta = new ArrayList<>();
    private AdapterEditorGrupo adapterEditorGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_de_grupos);
        Bundle dados = getIntent().getExtras();
        nomeConjunto = dados.getString("conjunto");
        nomeGrupo = dados.getString("grupo");
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
    }

    public void edgVoltar(View view){
        finish();
    }

    public void edgCriaCarta (View view){
        int numero = listaDeCarta.size() + 1;
        String nomeCarta;
        if (numero < 10){
            nomeCarta = "00" + numero;
        }else{
            nomeCarta = "0" + numero;
        }
        Intent intent = new Intent(EditorDeGruposActivity.this, EditorCartaGrupoActivity.class);
        intent.putExtra("conjunto", nomeConjunto);
        intent.putExtra("grupo", nomeGrupo);
        intent.putExtra("numerocarta", nomeCarta);
        intent.putExtra("frente", "");
        intent.putExtra("verso", "");
        intent.putExtra("elocal", "");
        startActivity(intent);
    }

    public void listarCartaGrupo(){
        RecyclerView recyclerView = findViewById(R.id.recycler_editor_grupo);
        //TODO: Colocar no preference o nome do conjunto e do grupo para não dar mais problema
        usuario = database
                .child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto")
                .child(nomeConjunto)
                .child("lista grupos")
                .child(nomeGrupo)
                .child("lista cartas");
        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeCarta.clear();
                for (DataSnapshot dados: snapshot.getChildren()){
                    CartaGrupo carta = dados.getValue(CartaGrupo.class);
                    listaDeCarta.add(carta);
                }
                adapterEditorGrupo.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        adapterEditorGrupo = new AdapterEditorGrupo( this,listaDeCarta, nomeConjunto, nomeGrupo);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterEditorGrupo);

    }
    @Override
    protected void onStart() {
        super.onStart();
        listarCartaGrupo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuario.removeEventListener(valueEventListenerUsuario);
    }
}