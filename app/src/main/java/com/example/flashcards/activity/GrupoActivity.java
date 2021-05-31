package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;
import com.example.flashcards.adapter.AdapterConjunto;
import com.example.flashcards.adapter.AdapterGrupo;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.model.AuxiliarConjunto;
import com.example.flashcards.model.Conjunto;
import com.example.flashcards.model.Grupo;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrupoActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private final DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private String nomeConjunto;
    private ValueEventListener valueEventListenerUsuario;
    private SharedPreferences.Editor editor;
    private Button habilitar;
    private List<AuxiliarConjunto> listaAux = new ArrayList<>();
    private List<Grupo> listaDeGrupo = new ArrayList<>();
    private AdapterGrupo adapterGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        habilitar = findViewById(R.id.grupo_btn_habilitar);

        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
        editor = preferences.edit();
        editor.putInt("tamanho", 0);
        editor.apply();

        Bundle dados = getIntent().getExtras();
        TextView textView = findViewById(R.id.grupo_txt_conjunto);
        nomeConjunto = dados.getString("conjunto");
        textView.setText(nomeConjunto);

        if(!verificarConjuntoAtivo()){
            habilitar.setText(R.string.grupo_habilitar);
        }else{
            habilitar.setText(R.string.grupo_desabilitar);
        }
    }

    public void grupoVoltar(View view){
        finish();
    }

    public boolean verificarConjuntoAtivo(){
        //todo: resolver problema, caso contrário poderá dar problema nas activitys mais importantes
        usuario = database
                .child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto")
                .child(nomeConjunto)
                .child("termino");

        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaAux.clear();
                try {
                    for (DataSnapshot dados : snapshot.getChildren()) {
                        AuxiliarConjunto aux = dados.getValue(AuxiliarConjunto.class);
                        listaAux.add(aux);
                    }
                    SharedPreferences preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("termino", listaAux.get(0).isTermino());
                    editor.apply();
                    Log.i("FIREBASE", "lista AUX: " + listaAux.get(0).isTermino());
                }catch (Exception e){
                    Log.i("FIREBASE", "erro: " + e);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO: Fazer tratamento de errro
            }
        });
        Log.i("FIREBASE", "Arquivo pref: " + preferences.getBoolean("termino", false));
        return preferences.getBoolean("termino", false);
    }

    public void grupoAddGrupo(View view){

        usuario = database
                .child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto")
                .child(nomeConjunto)
                .child("lista grupos");

        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Grupo> listedGroup = new ArrayList<>();
                int k = 0;
                for(DataSnapshot dados: snapshot.getChildren()){
                    try {
                        Grupo grupo = dados.getValue(Grupo.class);
                        listedGroup.add(grupo);
                        k++;
                    }catch ( Exception e){
                        Log.i("flashCards", "erro" + e);
                    };
                }
                editor.putInt("tamanho", k);
                editor.apply();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO: Fazer tratamento de errro
            }
        });

        Grupo grupo = new Grupo();
        grupo.setOrdem(preferences.getInt("tamanho", 0));
        String orden = "";
        if(grupo.getOrdem()<9){
            orden = "00";
        }else if(grupo.getOrdem()<99){
            orden = "0";
        }
        grupo.setNome(orden + (grupo.getOrdem() + 1));
        database.child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto")
                .child(nomeConjunto)
                .child("lista grupos")
                .child(grupo.getNome())
                .setValue(grupo);
        AuxiliarConjunto aux = new AuxiliarConjunto(false);
        database.child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto")
                .child(nomeConjunto)
                .child("lista grupos")
                .child(grupo.getNome())
                .child("termino")
                .setValue(aux);


        Intent irGrupo = new Intent(GrupoActivity.this, EditorDeGruposActivity.class);
        irGrupo.putExtra("grupo", grupo.getNome());
        startActivity(irGrupo);
    }

    public void grupoHabilitar(View view){
        boolean habilitado;
        if(!verificarConjuntoAtivo()){
            habilitar.setText(R.string.grupo_habilitar);
            habilitado = false;
        }else{
            habilitar.setText(R.string.grupo_desabilitar);
            habilitado = true;
        }
        Map<String, Object> mapa = new HashMap<>();
        AuxiliarConjunto auxiliarConjunto = new AuxiliarConjunto(habilitado);
        mapa.put("termino", auxiliarConjunto);

        Task<Void> usuario2;
        usuario2 = database
                .child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto")
                .child(nomeConjunto)
                .updateChildren(mapa);
    }

    public void listarGrupo(){
        RecyclerView recyclerView = findViewById(R.id.recycler_grupo);
        usuario = database
                .child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto")
                .child(nomeConjunto)
                .child("lista grupos");
        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeGrupo.clear();
                for (DataSnapshot dados: snapshot.getChildren()){
                    Grupo grupo = dados.getValue(Grupo.class);
                    listaDeGrupo.add(grupo);
                }
                adapterGrupo.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapterGrupo = new AdapterGrupo(this, listaDeGrupo, nomeConjunto);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterGrupo);

    }

    @Override
    protected void onStart() {
        super.onStart();
        listarGrupo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuario.removeEventListener(valueEventListenerUsuario);
    }

}