package com.example.flashcards.activity;

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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class TextoeAudiosActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
    private final DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerUsuario;
    private SharedPreferences preferences;


    private TextView texto1;
    private TextView texto2;
    private TextView texto3;
    private TextView texto4;
    private TextView texto5;
    private TextView texto6;
    private TextView texto7;
    private TextView texto8;
    private int funcao;
    private Button avancar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textoe_audios);

        texto1 = findViewById(R.id.tata_tx_1);
        texto2 = findViewById(R.id.tata_tx_2);
        texto3 = findViewById(R.id.tata_tx_3);
        texto4 = findViewById(R.id.tata_tx_4);
        texto5 = findViewById(R.id.tata_tx_5);
        texto6 = findViewById(R.id.tata_tx_6);
        texto7 = findViewById(R.id.tata_tx_7);
        texto8 = findViewById(R.id.tata_tx_8);
        funcao = 0;
        avancar = findViewById(R.id.tata_btn_avancar);
    }

    public void tataAvancar(View view) {
        usuario = database
                .child(preferences.getString("email",""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto");

        avancar.setEnabled(false);
        switch (funcao) {
            case 0:
                funcaoZero();
                break;
            case 1:
                funcaoUm();
                break;
            case 2:
                funcaoDois();
                break;
            case 3:
                funcaoTres();
                break;
            case 4:
                funcaoQuatro();
                break;
        }
        funcao++;
        avancar.setEnabled(true);
    }

    public void funcaoZero(){
        Toast.makeText(getApplicationContext(),"entrou na ZERO", Toast.LENGTH_LONG).show();
        //TimeUnit.SECONDS.sleep(1);

        try {
            //criação da lista

            valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //listaDeBaralhos.clear();
                    for (DataSnapshot dados : snapshot.getChildren()) {
                        Baralho baralho = dados.getValue(Baralho.class);
                        //listaDeBaralhos.add(baralho);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //TODO: Fazer tratamento de errro
                }
            });
        }catch (Exception e){
            Log.i("FIREBASE" , "Erro: " + e);
        }

    } 
    public void funcaoUm(){
        Toast.makeText(getApplicationContext(),"entrou na UM", Toast.LENGTH_LONG).show();
        avancar.setEnabled(true);
    }
    public void funcaoDois(){
        Toast.makeText(getApplicationContext(),"entrou na DOIS", Toast.LENGTH_LONG).show();
        avancar.setEnabled(true);
    }
    public void funcaoTres(){
        Toast.makeText(getApplicationContext(),"entrou na TRES", Toast.LENGTH_LONG).show();
        avancar.setEnabled(true);
    }
    public void funcaoQuatro(){
        Toast.makeText(getApplicationContext(),"entrou na QUATRO", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(TextoeAudiosActivity.this, CartaActivity.class);
        startActivity(intent);
        finish();
    }

    public void salvarCartas(){

    }

}






