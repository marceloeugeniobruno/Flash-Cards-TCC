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
        //TODO: ve dados de entrada do programa de estudos
        //TODO: ve que dia é agora e verifica com o último dia de acesso a este baralho
        //TODO: ve a diferênça (dia atual - ultimo dia estudado)
        //TODO: aplica a fómula (MAX  0 , dia da carta (D) - a diferença) em todas a cartas do baralho
        //TODO: verifica quantas cartas o programa de estudos tem que mostrar para o usuário
        //TODO: conta o número de cartas com o (D) = a zero
        //TODO: se o numero de cartas de D = 0, for maior que o numero de cartas do programa de estudos,
        //TODO: o app faz um sorteio de qual quarta irá conter no programa,
        //TODO: caso o número de cartas com D = 0 seja menor, todas elas farão parte do programa de estudos
        //TODO: e o programa ira pegar as cartas com o D = 1, e faz a mesma lógica que fez com o D=0
        //TODO: e ele faz esse processo com o D++ até terminar o porgrama de estudos ou até terminar o deck

        //TODO: pegar a primeira carta do programa de estudos Mostrar a frente e se tiver áudio,
        //TODO: tocar o áudio

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