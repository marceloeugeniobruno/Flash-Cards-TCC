
package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.example.flashcards.helper.Permissoes;
import com.example.flashcards.model.Carta;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditorDeCartasVersoActivity extends AppCompatActivity {
    //botões
    private Button excAudio;
    private Button addAudio;

    //texto views
    private TextView infoAudio;
    private EditText textoVerso;
    //variaveis
    private int flag;
    private String nomeBaralho;
    private String tipo;
    private String email;

    //variaveis do verso
    private String versoTexto = "";
    private String versoAudio = "";


    //arquivo de preferências
    private SharedPreferences preferences;
    //referencia do storage do firebase
    private StorageReference storage;
    //cria o objeto carta
    Carta carta;

    private boolean uploadOK = true;
    private String enderecoUtil;

    private String [] permissoees = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    /*
    private Button excImagem;
    private Button excVideo;
    private Button addImagem;
    private Button addVideo;
    private TextView infoImagem;
    private TextView infoVideo;
    private String versoImagem = "";
    private String versoVideo = "";

     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_de_cartas_verso);
        /*
        excImagem= findViewById(R.id.edcv_btn_img_exc);
        excVideo = findViewById(R.id.edcv_btn_vid_exc);
        addImagem= findViewById(R.id.edcv_btn_img);
        addVideo = findViewById(R.id.edcv_btn_vid);
        infoImagem= findViewById(R.id.edcv_txt_inf_img);
        infoVideo = findViewById(R.id.edcv_txt_inf_vid);
        versoImagem = preferences.getString("endIV", "");
        if(!versoImagem.equals("")){
            alteraBttImagem(versoImagem);
        }
        versoVideo = preferences.getString("endVV", "");
        if(!versoVideo.equals("")){
            alteraBttVideo(versoVideo);
        }
         */

        Permissoes.validarPermissoes(permissoees, this, 1);
        excAudio = findViewById(R.id.edcv_btn_aud_exc);
        addAudio = findViewById(R.id.edcv_btn_aud);
        infoAudio = findViewById(R.id.edcv_txt_inf_aud);
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

        //para flag = 2 que é para textos com áudio, não é para colocar nem vídeos nem imagens

        if(flag == 2 || flag == 4){
            addAudio.setVisibility(View.INVISIBLE);
            //addImagem.setVisibility(View.INVISIBLE);
            //addVideo.setVisibility(View.INVISIBLE);
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
            if (requestCode == 1){
                alteraBttAudio(data.getDataString());
            }
            /*
            else if (requestCode == 2){
                alteraBttImagem(data.getDataString());
            }else if(requestCode == 3){
                alteraBttVideo(data.getDataString());
            }
             */
        }
    }


    public void addMaisUmaCarta(View view){
        Intent intent = new Intent(EditorDeCartasVersoActivity.this, EditorDeCartasActivity.class);
        intent.putExtra("tipo", tipo);
        intent.putExtra("nomeBaralho", nomeBaralho);
        intent.putExtra("flag", flag);
        criarCarta();
        if(verificar()) {
            limparESalvar();
            startActivity(intent);
            finish();
        }
    }

    public void salvarEFechar(View view){
        criarCarta();
        if(verificar()) {
            limparESalvar();
            finish();
        }
    }

    public void criarCarta(){
        //cria objeto e insere as variaveis iniciais dos objetos.
        carta = new Carta(nomeBaralho);
        if (flag > 2) {
            carta.setDias(0);
            carta.setMultiplicador(0);
        }

        carta.setTextoFrente(preferences.getString("textoFrente", ""));
        carta.setTextoVerso(textoVerso.getText().toString());
        carta.setEndAudioFrente("");
        carta.setEndAudioVerso("");

        DatabaseReference firebase = ConfiguracaoFirebase.getDatabase();
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
        email = Base64Custon.codificarBase64(autenticacao.getCurrentUser().getEmail());
        firebase.child(email)
                .child(nomeBaralho)
                .child("listaCartas")
                .child(carta.getIdentificador())
                .setValue(carta);
    }

    public boolean verificar(){
        versoTexto = textoVerso.getText().toString();
        versoAudio = infoAudio.getText().toString();
        /*
        versoImagem = infoImagem.getText().toString();
        versoVideo = infoVideo.getText().toString();
        if (!versoAudio.equals("")|| !versoTexto.equals("")|| !versoImagem.equals("")|| !versoVideo.equals("")) {
         */


        if (!versoAudio.equals("")|| !versoTexto.equals("")) {
            if (flag == 1 || flag == 3) {
                if (!upLoadsarquivos()){
                    return false;
                }
                return true;
            }else if (flag == 2|| flag == 4){
                if (!versoTexto.equals("")){
                    return true;
                }else{
                    Toast.makeText(EditorDeCartasVersoActivity.this,
                            R.string.edc_alerta_grupo_sem_audio,
                            Toast.LENGTH_LONG).show();
                    DatabaseReference firebase = ConfiguracaoFirebase.getDatabase();
                    firebase.child(email)
                            .child(nomeBaralho)
                            .child("listaCartas")
                            .child(carta.getIdentificador())
                            .removeValue();
                }
            }
        }else{
            Toast.makeText(EditorDeCartasVersoActivity.this,
                    R.string.edc_alerta_sem_preecimento,
                    Toast.LENGTH_LONG).show();
            DatabaseReference firebase = ConfiguracaoFirebase.getDatabase();
            firebase.child(email)
                    .child(nomeBaralho)
                    .child("listaCartas")
                    .child(carta.getIdentificador())
                    .removeValue();
        }

        return false;
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

    public boolean upLoadsarquivos(){

        versoAudio = infoAudio.getText().toString();
        String frenteAudio = preferences.getString("endAF", "");
        DatabaseReference firebase = ConfiguracaoFirebase.getDatabase();


        if(!versoAudio.equals("")){
            upLoad(versoAudio, "versoAudio.mp3", "endAudioVerso");
            }
        if(!frenteAudio.equals("")){
            upLoad(frenteAudio, "frenteAudio.mp3", "endAudioFrente");
        }
        return uploadOK;

        /*
        //todo: implementar itens depois da entraga do Artigo
        Essa função verifica se tem algum arquivo anexado e
        versoImagem = infoImagem.getText().toString();
        versoVideo = infoVideo.getText().toString();
        if(!versoImagem.equals("")){
            upLoad(versoImagem, "versoImagem.jpg");
        }
        if(!versoVideo.equals("")){
            upLoad(versoVideo, "versoVideo.mp4");
        }
        String frenteImagem = preferences.getString("endIF", "");
        String frenteVideo = preferences.getString("endVF", "");

        if(!frenteImagem.equals("")){
            upLoad(frenteImagem, "frenteImagemm.jpg");
        }
        if(!frenteVideo.equals("")){
            upLoad(frenteVideo, "frenteVideo.mp4");
        }
         */
    }

    public void upLoad(String url, String nome,String onde){
        storage = ConfiguracaoFirebase.getFirebaseStorage();
        final StorageReference salvararquivos = storage
                .child(email)
                .child(nomeBaralho)
                .child(carta.getIdentificador())
                .child(nome);

        //Fazer upload do arquivo
        UploadTask uploadTask = salvararquivos.putFile( Uri.parse(url) );
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>(){
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                salvararquivos.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri endereco = task.getResult();
                        assert endereco != null;
                        DatabaseReference firebase = ConfiguracaoFirebase.getDatabase();
                        firebase.child(email)
                                .child(nomeBaralho)
                                .child("listaCartas")
                                .child(carta.getIdentificador())
                                .child(onde)
                                .setValue(endereco.toString());

                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadOK = false;
            }
        });
    }


    public void edcvVoltar(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("textoVerso", textoVerso.getText().toString());
        editor.putString("endAV", infoAudio.getText().toString());

        editor.apply();

        Intent intent = new Intent(EditorDeCartasVersoActivity.this, EditorDeCartasActivity.class);
        intent.putExtra("tipo", tipo);
        intent.putExtra("nomeBaralho", nomeBaralho);
        intent.putExtra("flag", flag);

        startActivity(intent);
        finish();

        /*
        editor.putString("endIV", infoImagem.getText().toString());
        editor.putString("endVV", infoVideo.getText().toString());
         */
    }

    /*
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


}