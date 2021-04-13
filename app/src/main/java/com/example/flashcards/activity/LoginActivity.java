package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.flashcards.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void esquiciSenha(View view){
        Intent esqueci = new Intent(getApplicationContext(), EsqueciSenhaActivity.class);
        startActivity(esqueci);
    }

    public void loginFinalizar(View view){
        finish();
    }

}