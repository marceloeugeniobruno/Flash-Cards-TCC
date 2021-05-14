package com.example.flashcards.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    //arquivo de preferências
    private SharedPreferences preferences;


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

        //arquivo de preferencias
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);

        //recuperando dados
        Bundle dados = getIntent().getExtras();
        nomeBaralho = dados.getString("nomeBaralho");
        tipo = dados.getString("tipo");
        flag = dados.getInt("flag");

        textoVerso.setText(preferences.getString("textoVerso", ""));

        versoAudio = preferences.getString("endAV", "");
        if(!versoAudio.equals("")){
            alteraBttAudio(versoAudio);
        }

        versoImagem = preferences.getString("endIV", "");
        if(!versoImagem.equals("")){
            alteraBttImagem(versoImagem);
        }

        versoVideo = preferences.getString("endVV", "");
        if(!versoVideo.equals("")){
            alteraBttVideo(versoVideo);
        }
        //para flag = 2 que é para textos com áudio, não é para colocar nem vídeos nem imagens

        if(flag == 2){
            addImagem.setVisibility(View.INVISIBLE);
            addVideo.setVisibility(View.INVISIBLE);
        }


    }

    public void edcvAdicionarAudio(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    public void edcvAdicionarImagem(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);

    }

    public void edcvAdicionarVideo(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 3);
    }

    public void excvAudio(View view){
        excAudio.setVisibility(View.INVISIBLE);
        infoAudio.setText("");
        infoAudio.setVisibility(View.INVISIBLE);
        addAudio.setVisibility(View.VISIBLE);
    }

    public void excvImagem(View view){
        excImagem.setVisibility(View.INVISIBLE);
        infoImagem.setText("");
        infoImagem.setVisibility(View.INVISIBLE);
        addImagem.setVisibility(View.VISIBLE);
    }

    public void excvVideo(View view){
        excVideo.setVisibility(View.INVISIBLE);
        infoVideo.setText("");
        infoVideo.setVisibility(View.INVISIBLE);
        addVideo.setVisibility(View.VISIBLE);
    }

    public void alteraBttAudio(String endereco){
        excAudio.setVisibility(View.VISIBLE);
        infoAudio.setText(endereco);
        infoAudio.setVisibility(View.VISIBLE);
        addAudio.setVisibility(View.INVISIBLE);
    }

    public void alteraBttImagem(String endereco){
        excImagem.setVisibility(View.VISIBLE);
        infoImagem.setText(endereco);
        infoImagem.setVisibility(View.VISIBLE);
        addImagem.setVisibility(View.INVISIBLE);
    }

    public void alteraBttVideo(String endereco){
        excVideo.setVisibility(View.VISIBLE);
        infoVideo.setText(endereco);
        infoVideo.setVisibility(View.VISIBLE);
        addVideo.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            assert data != null;
            if (requestCode == 1){
                alteraBttAudio(data.getDataString());
            }else if (requestCode == 2){
                alteraBttImagem(data.getDataString());
            }else if(requestCode == 3){
                alteraBttVideo(data.getDataString());
            }
        }
    }

    public void limparESalvar(){
        //TODO: Criar processo de salvamento das cartas

        SharedPreferences.Editor editor = preferences.edit();
        //variaveis da carta frente
        editor.putString("textoFrente","");
        editor.putString("endAF","");
        editor.putString("endIF","");
        editor.putString("endVF","");
        //variaveis da carta verso
        editor.putString("textoVerso","");
        editor.putString("endAV","");
        editor.putString("endIV","");
        editor.putString("endVV","");
        editor.commit();
    }


    public void addMaisUmaCarta(View view){
        Intent intent = new Intent(EditorDeCartasVersoActivity.this, EditorDeCartasActivity.class);
        intent.putExtra("tipo", tipo);
        intent.putExtra("nomeBaralho", nomeBaralho);
        intent.putExtra("flag", flag);
        if(verificar()) {
            limparESalvar();
            startActivity(intent);
            finish();
        }
    }

    public void salvarEFechar(View view){
        if(verificar()) {
            limparESalvar();
            finish();
        }
    }

    public boolean verificar(){
        versoTexto = textoVerso.getText().toString();
        versoAudio = infoAudio.getText().toString();
        versoImagem = infoImagem.getText().toString();
        versoVideo = infoVideo.getText().toString();

        if (!versoAudio.equals("")|| !versoTexto.equals("")|| !versoImagem.equals("")|| !versoVideo.equals("")) {
            if (flag == 1) {
                return true;
            }else if (flag == 2){
                if (!versoAudio.equals("") && !versoTexto.equals("")){
                    return true;
                }else{
                    Toast.makeText(EditorDeCartasVersoActivity.this,
                            R.string.edc_alerta_grupo_sem_audio,
                            Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(EditorDeCartasVersoActivity.this,
                    R.string.edc_alerta_sem_preecimento,
                    Toast.LENGTH_LONG).show();
        }

        return false;
    }


    public void edcvVoltar(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("textoVerso", textoVerso.getText().toString());
        editor.putString("endAV", infoAudio.getText().toString());
        editor.putString("endIV", infoImagem.getText().toString());
        editor.putString("endVV", infoVideo.getText().toString());
        editor.apply();

        Intent intent = new Intent(EditorDeCartasVersoActivity.this, EditorDeCartasActivity.class);
        intent.putExtra("tipo", tipo);
        intent.putExtra("nomeBaralho", nomeBaralho);
        intent.putExtra("flag", flag);

        startActivity(intent);
        finish();
    }
}