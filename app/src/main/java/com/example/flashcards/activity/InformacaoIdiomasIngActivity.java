package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.flashcards.R;

public class InformacaoIdiomasIngActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao_idiomas_ing);
    }


    public void iiiAvancar(View view){
        finish();
        Intent principal = new Intent(InformacaoIdiomasIngActivity.this,
                InformacaoIdiomasActivity.class);
        startActivity(principal);
    }
}