package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.flashcards.R;
import com.example.flashcards.helper.Permissoes;

public class EditorDeCartasVersoActivity extends AppCompatActivity {
    //botões
    private Button excAudio;
    private Button excImagem;
    private Button excVideo;
    private Button addAudio;
    private Button addImagem;
    private Button addVideo;
    //texto views
    private TextView infoAudio;
    private TextView infoImagem;
    private TextView infoVideo;
    private EditText textoVerso;
    //variaveis
    private int flag;
    private String nomeBaralho;
    private String tipo;

    //variaveis do verso
    private String versoTexto = "";
    private String versoAudio = "";
    private String versoImagem = "";
    private String versoVideo = "";


    private String [] permissoees = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_de_cartas_verso);

        Permissoes.validarPermissoes(permissoees, this, 1);
        excAudio = findViewById(R.id.edcv_btn_aud_exc);
        excImagem= findViewById(R.id.edcv_btn_img_exc);
        excVideo = findViewById(R.id.edcv_btn_vid_exc);

        addAudio = findViewById(R.id.edcv_btn_aud);
        addImagem= findViewById(R.id.edcv_btn_img);
        addVideo = findViewById(R.id.edcv_btn_vid);

        infoAudio = findViewById(R.id.edcv_txt_inf_aud);
        infoImagem= findViewById(R.id.edcv_txt_inf_img);
        infoVideo = findViewById(R.id.edcv_txt_inf_vid);
        textoVerso = findViewById(R.id.edcv_edt_verso);

        Bundle dados = getIntent().getExtras();
        nomeBaralho = dados.getString("nomeBaralho");
        tipo = dados.getString("tipo");
        flag = dados.getInt("flag");


    }

    public void edcvVoltar(View view){
        Intent intent = new Intent(EditorDeCartasVersoActivity.this, EditorDeCartasActivity.class);
        intent.putExtra("tipo", tipo);
        intent.putExtra("nomeBaralho", nomeBaralho);
        intent.putExtra("flag", flag);

        startActivity(intent);
        finish();
    }
}