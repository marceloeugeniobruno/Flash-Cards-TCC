package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.flashcards.R;

public class MainActivity extends AppCompatActivity {

    protected final static String ARQUIVO_PREFERENCIAS = "arquivoPreferencia";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }

    @Override
    protected void onStart() {
        super.onStart();
        //TODO: criar algoritimo de usuário logado
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();

        if (preferences.contains("logado")){
            boolean logado = preferences.getBoolean("logado", false);
            if (!logado){
                naologado();
            }
        }else{
            editor.putBoolean("logado", false);
            editor.commit();
            naologado();
        }
    }

    public void naologado(){
        Intent inicial = new Intent(getApplicationContext(), InicialActivity.class);
        startActivity(inicial);
    }

}