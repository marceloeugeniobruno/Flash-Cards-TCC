package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.flashcards.R;

public class InicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
    }

    public void criarUsuario(View view){
        Intent criarUsu = new Intent(getApplicationContext(), CadastroActivity.class);
        startActivity(criarUsu);
    }
    public void logarUsuario(View view){
        Intent logarUsu = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(logarUsu);
    }
}