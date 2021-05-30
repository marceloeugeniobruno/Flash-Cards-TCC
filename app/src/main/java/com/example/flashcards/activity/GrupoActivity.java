package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flashcards.R;
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
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GrupoActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private final DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private String nomeConjunto;
    private ValueEventListener valueEventListenerUsuario;
    private SharedPreferences.Editor editor;
    private Button habilitar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        habilitar = findViewById(R.id.grupo_btn_habilitar);

        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
        editor = preferences.edit();
        editor.putInt("tamanho", 0);
        editor.apply();

        if(verificarConjuntoAtivo()){
            habilitar.setText(R.string.grupo_habilitar);
        }else{
            habilitar.setText(R.string.grupo_desabilitar);
        }

        Bundle dados = getIntent().getExtras();
        TextView textView = findViewById(R.id.grupo_txt_conjunto);
        nomeConjunto = dados.getString("conjunto");
        textView.setText(nomeConjunto);

    }

    public void grupoVoltar(View view){
        finish();
    }

    public boolean verificarConjuntoAtivo(){
        usuario = database
                .child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto");
        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dados: snapshot.getChildren()){
                    try {
                        Conjunto conjunto = dados.getValue(Conjunto.class);
                        assert conjunto != null;
                        if(nomeConjunto.equals(conjunto.getNome())){
                            SharedPreferences preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
                            SharedPreferences.Editor editor = preferences.edit();
                        }
                    }catch ( Exception e){
                        Log.i("flashCards", "erro : " + e);
                    };
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO: Fazer tratamento de errro
            }
        });

        return !preferences.getBoolean("conjuntoTermino", true);
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
        grupo.setExecutado(false);
        database.child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto")
                .child(nomeConjunto)
                .child("lista grupos")
                .child(grupo.getNome())
                .setValue(grupo);

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
        Conjunto conjunto = new Conjunto();
        conjunto.setNome(nomeConjunto);
        mapa.put(nomeConjunto, conjunto);

        Task<Void> usuario2;
        usuario2 = database
                .child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto")
                .updateChildren(mapa);
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuario.removeEventListener(valueEventListenerUsuario);
    }

}