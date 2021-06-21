package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.flashcards.R;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.model.Carta;
import com.example.flashcards.model.Conjunto;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CartaActivity extends AppCompatActivity {


    private final DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerUsuario;
    private SharedPreferences preferences;

    private Button btn_mostrar;
    private Button btn_errei;
    private Button btn_dificil;
    private Button btn_Facil;
    private Button btn_iniciar;
    private TextView tex_frente;
    private TextView text_verso;
    private int ncarta;
    private List<Carta> listaCartas = new ArrayList<>();
    private final List<Integer> indices = new ArrayList<>();
    private int dias;
    private int mostrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);
        btn_mostrar = findViewById(R.id.tafc_btn_mostrar);
        btn_errei = findViewById(R.id.tafc_btn_errei);
        btn_dificil = findViewById(R.id.tafc_btn_dif);
        btn_Facil = findViewById(R.id.tafc_btn_fac);
        btn_iniciar = findViewById(R.id.tafc_btn_init);
        tex_frente = findViewById(R.id.tafc_txt_fr);
        text_verso = findViewById(R.id.tafc_txt_ve);
        dias = 1; //todo: iplementar algoritmo de para ver quantos dias esse baralho não foi estudado
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
        ncarta = preferences.getInt("nCartas", 0);
        mostrar = 0;
        usuario = database
                .child(preferences.getString("email",""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("listaCartas");
        Log.i("marcelo firebase", "Nº Cartas " + ncarta);
        baixarCartas();
    }

    public void iniciar(View view){
        criarLista();
        mostrarCarta();
        btn_iniciar.setVisibility(View.INVISIBLE);
        btn_mostrar.setVisibility(View.VISIBLE);
        tex_frente.setVisibility(View.VISIBLE);

        Log.i("marcelo firebase ", listaCartas.size() +"");

    }

    private void baixarCartas() {
        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaCartas.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Carta carta = dados.getValue(Carta.class);
                    listaCartas.add(carta);
                }
                for(int i = 0; i < listaCartas.size();i++){
                    int d = Math.max(0, listaCartas.get(i).getDias() - dias);
                    listaCartas.get(i).setDias(d);
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void criarLista() {
        int aux = 0;
        List<Integer> listaAux = new ArrayList<>();
        if(ncarta > listaCartas.size()){
            for(int k = 0; k < listaCartas.size(); k++){
                indices.add(k);
            }
        }else {
            while (ncarta < indices.size()) {
                for (int i = 0; i < listaCartas.size(); i++) {
                    if (aux == listaCartas.get(i).getDias()) {
                        listaAux.add(i);
                    }
                }
                if (listaAux.size() < ncarta) {
                    indices.addAll(listaAux);
                    aux++;
                    listaAux.clear();
                } else {
                    for (int j = 0; j < ncarta - indices.size(); j++) {
                        indices.add(listaAux.get(j));
                    }
                }
            }
        }
    }

    public void mostrarCarta(){
        if (mostrar < indices.size()){
            tex_frente.setText(listaCartas.get(mostrar).getTextoFrente());
            text_verso.setText(listaCartas.get(mostrar).getTextoVerso());
            //TODO: tocar áudio se tiver

        }else{
            finalizar();
        }

    }

    private void finalizar() {
        for(int i = 0; i < listaCartas.size(); i++) {
            database.child(preferences.getString("email", ""))
                    .child(preferences.getString("nomeBaralho", ""))
                    .child("listaCartas")
                    .child(listaCartas.get(i).getIdentificador())
                    .setValue(listaCartas.get(i));
        }


        finish();
    }

    public void tafcMostrar(View view){

     btn_dificil.setVisibility(View.VISIBLE);
     btn_errei.setVisibility(View.VISIBLE);
     btn_Facil.setVisibility(View.VISIBLE);
     text_verso.setVisibility(View.VISIBLE);
     btn_mostrar.setVisibility(View.INVISIBLE);
    }

    public void tafcOcultarBotoees(){
        btn_dificil.setVisibility(View.INVISIBLE);
        btn_errei.setVisibility(View.INVISIBLE);
        btn_Facil.setVisibility(View.INVISIBLE);
        text_verso.setVisibility(View.INVISIBLE);
        btn_mostrar.setVisibility(View.VISIBLE);
    }

    public void tafcErrei(View view){
        tafcOcultarBotoees();
        listaCartas.get(mostrar).errei();
        mostrar++;
        mostrarCarta();
    }
    public void tafcDificil(View view){
        tafcOcultarBotoees();
        listaCartas.get(mostrar).dificil();
        mostrar++;
        mostrarCarta();
    }
    public void tafcFacil(View view){
        tafcOcultarBotoees();
        listaCartas.get(mostrar).facil();
        mostrar++;
        mostrarCarta();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuario.removeEventListener(valueEventListenerUsuario);
    }
}