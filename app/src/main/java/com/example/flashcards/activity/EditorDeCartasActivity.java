package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.helper.Permissoes;

public class EditorDeCartasActivity extends AppCompatActivity {
    //botões
    private Button excAudio;
    private Button addAudio;

    //campos textos
    private TextView infoAudio;
    private EditText textoFrente;
    //variáveis globais
    private int flag;
    private String nomeBaralho;
    private String tipo;
    //variáveis frente
    private String frenteTexto = "";
    private String frenteAudio;

    /*
    private Button excImagem;
    private Button excVideo;
    private Button addImagem;
    private Button addVideo;
    private TextView infoImagem;
    private TextView infoVideo;
    private String frenteImagem;
    private String frenteVideo;

     */

    //arquivo de preferências
    private SharedPreferences preferences;

    private String [] permissoees = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_de_cartas);
        Permissoes.validarPermissoes(permissoees, this, 1);
        //botões
        excAudio = findViewById(R.id.edc_btn_aud_ex);
      //  excImagem= findViewById(R.id.edc_btn_img_ex);
      //  excVideo = findViewById(R.id.edc_btn_vid_ex);

        addAudio = findViewById(R.id.edc_btn_aud);
      //  addImagem= findViewById(R.id.edc_btn_img);
      //  addVideo = findViewById(R.id.edc_btn_vid);

        //text views
        infoAudio = findViewById(R.id.edc_txt_inf_aud);
     //   infoImagem= findViewById(R.id.edc_txt_inf_img);
     //   infoVideo = findViewById(R.id.edc_txt_inf_vid);
        textoFrente = findViewById(R.id.edc_edt_frente);
        textoFrente.setText("");
        //arquivo de preferencias
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);

        //recuperando dados
        Bundle dados = getIntent().getExtras();
        nomeBaralho = dados.getString("nomeBaralho");
        tipo = dados.getString("tipo");
        flag = dados.getInt("flag");
        //textos frente
        textoFrente.setText(preferences.getString("textoFrente", ""));

        frenteAudio = preferences.getString("endAF", "");
        if(!frenteAudio.equals("")){
            alteraBttAudio(frenteAudio);
        }
        /*
        frenteImagem = preferences.getString("endIF", "");
        if(!frenteImagem.equals("")){alteraBttImagem(frenteImagem);}
        frenteVideo = preferences.getString("endVF", "");
        if(!frenteVideo.equals("")){alteraBttVideo(frenteVideo);}
        //para flag = 2 que é para textos com áudio, não é para colocar nem vídeos nem imagens
        if(flag == 2){addImagem.setVisibility(View.INVISIBLE);addVideo.setVisibility(View.INVISIBLE);}
         */
    }


    public void edcAdicionarAudio(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);
    }

    public void edcAdicionarImagem(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);

    }

    public void edcAdicionarVideo(View view){
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 3);
    }

    public void excAudio(View view){
        excAudio.setVisibility(View.INVISIBLE);
        infoAudio.setText("");
        infoAudio.setVisibility(View.INVISIBLE);
        addAudio.setVisibility(View.VISIBLE);
    }
/*
    public void excImagem(View view){excImagem.setVisibility(View.INVISIBLE);infoImagem.setText("");
        infoImagem.setVisibility(View.INVISIBLE);addImagem.setVisibility(View.VISIBLE);}

    public void excVideo(View view){excVideo.setVisibility(View.INVISIBLE);infoVideo.setText("");
    infoVideo.setVisibility(View.INVISIBLE);addVideo.setVisibility(View.VISIBLE);}

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

 */

    public void alteraBttAudio(String endereco){
        excAudio.setVisibility(View.VISIBLE);
        infoAudio.setText(endereco);
        infoAudio.setVisibility(View.VISIBLE);
        addAudio.setVisibility(View.INVISIBLE);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            assert data != null;
            Uri arquivoSelecionado = data.getData();
            String caminho = arquivoSelecionado.toString();

            if (requestCode == 1){
                alteraBttAudio(caminho);
            }

            /*else if (requestCode == 2){alteraBttImagem(caminho);}else if(requestCode == 3)
            {alteraBttVideo(caminho);
            }
             */
        }
    }

    public void edcFinalizar(View view){

        SharedPreferences.Editor editor = preferences.edit();
        //flags da carta frente
        editor.putString("textoFrente","");
        editor.putString("endAF","");
        editor.putString("endIF","");
        editor.putString("endVF","");
        editor.putString("textoVerso","");
        editor.putString("endAV","");
        editor.putString("endIV","");
        editor.putString("endVV","");
        editor.apply();
        finish();

    }

    public void edcSalvar(View view){
        SharedPreferences.Editor editor = preferences.edit();
        String frenteTexto = textoFrente.getText().toString();
        frenteAudio = infoAudio.getText().toString();
        /*
        frenteVideo = infoVideo.getText().toString();
        frenteImagem = infoImagem.getText().toString();
        editor.putString("endIF", frenteImagem);
        editor.putString("endVF", frenteVideo);
         */

        editor.putString("textoFrente", frenteTexto);
        editor.putString("endAF", frenteAudio);
        editor.apply();

        Intent editcartaVerso = new Intent(EditorDeCartasActivity.this, EditorDeCartasVersoActivity.class);
        editcartaVerso.putExtra("tipo", tipo);
        editcartaVerso.putExtra("nomeBaralho", nomeBaralho);
        editcartaVerso.putExtra("flag", flag);

        if (!frenteAudio.equals("")
                || !frenteTexto.equals("")){
            if (flag == 1) {
                startActivity(editcartaVerso);
                finish();
            }else if (flag == 2){
                if (!frenteAudio.equals("") && !frenteTexto.equals("")){
                    startActivity(editcartaVerso);
                    finish();
                }else{
                    Toast.makeText(EditorDeCartasActivity.this,
                            R.string.edc_alerta_grupo_sem_audio,
                            Toast.LENGTH_LONG).show();
                }
            }
        }else{
            Toast.makeText(EditorDeCartasActivity.this,
                    R.string.edc_alerta_sem_preecimento,
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int permissaoResultado : grantResults){
            if (permissaoResultado == PackageManager.PERMISSION_DENIED){
                alertaValidacaoPermissoes();
            }
        }
    }

    private void alertaValidacaoPermissoes(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar adicionar uma carta é necessário Aceitar as permissões.");
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}