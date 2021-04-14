package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.flashcards.R;

public class MainActivity extends AppCompatActivity {
    private static boolean logado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TODO: criar algoritimo de usuário logado

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (logado){

        }else{
            logado = true;
            naologado();
        }
    }

    public void naologado(){
        Intent inicial = new Intent(getApplicationContext(), InicialActivity.class);
        startActivity(inicial);
    }

}