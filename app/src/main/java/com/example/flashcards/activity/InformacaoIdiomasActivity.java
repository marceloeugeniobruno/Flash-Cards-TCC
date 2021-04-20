package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.flashcards.R;

public class InformacaoIdiomasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacao__idiomas_);
    }

    public void iidAvancar(View view){
        finish();
        Intent principal = new Intent(InformacaoIdiomasActivity.this,
                BaralhoIdiomasActivity.class);
        startActivity(principal);
    }
}