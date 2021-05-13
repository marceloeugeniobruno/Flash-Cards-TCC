package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.helper.Permissoes;

public class EditorDeCartasActivity extends AppCompatActivity {

    private Button excAudio;
    private Button excImagem;
    private Button excVideo;
    private Button addAudio;
    private Button addImagem;
    private Button addVideo;
    private TextView infoAudio;
    private TextView infoImagem;
    private TextView infoVideo;
    private EditText textoFrente;
    private int flag;
    private String nomeBaralho;
    private String tipo;

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
        setContentView(R.layout.activity_editor_de_cartas);
        Permissoes.validarPermissoes(permissoees, this, 1);

        excAudio = findViewById(R.id.edc_btn_aud_ex);
        excImagem= findViewById(R.id.edc_btn_img_ex);
        excVideo = findViewById(R.id.edc_btn_vid_ex);

        addAudio = findViewById(R.id.edc_btn_aud);
        addImagem= findViewById(R.id.edc_btn_img);
        addVideo = findViewById(R.id.edc_btn_vid);

        infoAudio = findViewById(R.id.edc_txt_inf_aud);
        infoAudio.setText("");
        infoImagem= findViewById(R.id.edc_txt_inf_img);
        infoImagem.setText("");
        infoVideo = findViewById(R.id.edc_txt_inf_vid);
        infoVideo.setText("");
        textoFrente = findViewById(R.id.edc_edt_frente);
        textoFrente.setText("");

        Bundle dados = getIntent().getExtras();
        flag = dados.getInt("flag");
        nomeBaralho = dados.getString("nomeBaralho");
        tipo = dados.getString("tipo");
        if (flag == 2){
            textoFrente.setText(dados.getString("textofr"));
            String endau = dados.getString("endaudio");
            String endim = dados.getString("endimagem");
            String endvi = dados.getString("endvideo");
            if(!endau.equals("")){
                alteraBttAudio(endau);
            }
            if(!endim.equals("")){
                alteraBttAudio(endim);
            }
            if(!endvi.equals("")){
                alteraBttAudio(endvi);
            }
        }else if (flag == 4){
            versoTexto = dados.getString("versoTexto");
            versoAudio = dados.getString("versoAudio");
            versoImagem = dados.getString("versoImagem");
            versoVideo = dados.getString("versoVideo");
        }

        /*TODO: Adicionar método de verificação de oque está selecionado para quando o usuário
        TODO: voltar da Editar Verso os dados

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

    public void excImagem(View view){
        excImagem.setVisibility(View.INVISIBLE);
        infoImagem.setText("");
        infoImagem.setVisibility(View.INVISIBLE);
        addImagem.setVisibility(View.VISIBLE);
    }

    public void excVideo(View view){
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

    public void edcFinalizar(View view){
        finish();
    }

    public void edcSalvar(View view){
        String confereAudio = infoAudio.getText().toString();
        String confereFrente = textoFrente.getText().toString();
        String confereVideo = infoVideo.getText().toString();
        String confereImagem = infoImagem.getText().toString();

        Intent editcartaVerso = new Intent(EditorDeCartasActivity.this, EditorDeCartasVersoActivity.class);
        editcartaVerso.putExtra("tipo", tipo);
        editcartaVerso.putExtra("nomeBaralho", nomeBaralho);
        editcartaVerso.putExtra("frente", confereFrente);
        editcartaVerso.putExtra("endAudio", confereAudio);
        editcartaVerso.putExtra("endImagem", confereImagem);
        editcartaVerso.putExtra("endVideo", confereVideo);
        editcartaVerso.putExtra("flag", 1);
        editcartaVerso.putExtra("versoTexto", versoTexto);
        editcartaVerso.putExtra("versoImagem", versoImagem);
        editcartaVerso.putExtra("versoAudio", versoAudio);
        editcartaVerso.putExtra("versoVideo", versoVideo);

        if (!confereAudio.equals("")
                || !confereFrente.equals("")
                || !confereImagem.equals("")
                || !confereVideo.equals("")) {
            if (flag == 1 || flag == 2) {
                startActivity(editcartaVerso);
                finish();
            }else if (flag == 3 || flag == 4){
                if (!confereAudio.equals("")){
                    editcartaVerso.putExtra("flag", 3);
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