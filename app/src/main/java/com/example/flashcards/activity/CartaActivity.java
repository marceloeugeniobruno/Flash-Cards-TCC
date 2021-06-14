package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.flashcards.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CartaActivity extends AppCompatActivity {

    private Button btn_mostrar;
    private Button btn_errei;
    private Button btn_dificil;
    private Button btn_Facil;
    private TextView tex_frente;
    private TextView text_verso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carta);

        btn_mostrar = findViewById(R.id.tafc_btn_mostrar);
        btn_errei = findViewById(R.id.tafc_btn_errei);
        btn_dificil = findViewById(R.id.tafc_btn_dif);
        btn_Facil = findViewById(R.id.tafc_btn_fac);
        tex_frente = findViewById(R.id.tafc_txt_fr);
        text_verso = findViewById(R.id.tafc_txt_ve);

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
    }
    public void tafcDificil(View view){
        tafcOcultarBotoees();
    }
    public void tafcFacil(View view){
        tafcOcultarBotoees();
    }


}