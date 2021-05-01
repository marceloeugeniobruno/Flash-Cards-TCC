package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.flashcards.R;
import android.os.Bundle;
import android.widget.TextView;

public class PerguntaSegundaAnimoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pergunta_segunda_animo);
        Bundle dados = getIntent().getExtras();

        TextView t1 = findViewById(R.id.t1);
        t1.setText(dados.getString("nomeBaralho"));
        TextView t2 = findViewById(R.id.t2);
        t2.setText(dados.getString("tipo"));
        TextView t3 = findViewById(R.id.t3);
        t3.setText(String.valueOf(dados.getInt("nCartas")));

    }
}