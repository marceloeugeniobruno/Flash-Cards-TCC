package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.flashcards.R;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    protected final static String ARQUIVO_PREFERENCIAS = "arquivoPreferencia";
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }

    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getAuth();
        if(autenticacao.getCurrentUser() == null){
            Intent inicial = new Intent(getApplicationContext(), InicialActivity.class);
            startActivity(inicial);
        }

    }

    public void criarcaodeBaralho(View view){
        Intent inicial = new Intent(getApplicationContext(), CriarBaralhoActivity.class);
        startActivity(inicial);
    }

}