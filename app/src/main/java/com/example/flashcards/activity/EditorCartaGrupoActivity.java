package com.example.flashcards.activity;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flashcards.R;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.model.CartaGrupo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditorCartaGrupoActivity extends AppCompatActivity {
    private EditText frente;
    private EditText verso;
    private Button anexo;
    private TextView endereco;
    private SharedPreferences preferences;
    private String numerocarta;
    //private String conjunto;
    //private String grupo;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_carta_grupo);
        frente = findViewById(R.id.egc_edt_frente);
        verso = findViewById(R.id.egc_edt_verso);
        anexo = findViewById(R.id.egc_btn_anexar);
        endereco = findViewById(R.id.egc_txt_endlocal);
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
        Bundle dados = getIntent().getExtras();
        numerocarta = dados.getString("numerocarta");
        if(!dados.getString("frente").equals("")){
            frente.setText(dados.getString("frente"));
            verso.setText(dados.getString("verso"));
            endereco.setText(dados.getString("elocal"));
            anexo.setText(R.string.grupo_carta_edicao_add_excluir);
        }

    }
    public void egcVoltar(View view){
        finish();
    }

    public void egcAnexarExcluir(View view){
        String conf = anexo.getText().toString();
        if (conf.equals("Anexar áudio")){
            anexo.setText(R.string.grupo_carta_edicao_add_excluir);
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);
            //todo verificar problema da lina 65
        }else{
            anexo.setText(R.string.grupo_carta_edicao_add_nanexar);
            endereco.setText("");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            assert data != null;
            if (requestCode == 1){
                endereco.setText(data.getDataString());
            }
        }
    }

    public void egcSalvar   (View view){
        CartaGrupo cartaGrupo = new CartaGrupo();
        cartaGrupo.setFrente(frente.getText().toString());
        cartaGrupo.setVerso(verso.getText().toString());
        cartaGrupo.setEndLocal(endereco.getText().toString());
        if(!cartaGrupo.getVerso().equals("")){
            if(!cartaGrupo.getFrente().equals("")){
                if(!cartaGrupo.getEndLocal().equals("")){
                    cartaGrupo.setNome(numerocarta);
                    ecgUplaod(cartaGrupo);
                    finish();
                }else{
                    Toast.makeText(EditorCartaGrupoActivity.this,
                            R.string.grupo_carta_toast_semarquivo,
                            Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(EditorCartaGrupoActivity.this,
                        R.string.grupo_carta_toast_semfrente,
                        Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(EditorCartaGrupoActivity.this,
                    R.string.grupo_carta_toast_semaverso,
                    Toast.LENGTH_LONG).show();
        }
    }

    public String nomeCarta(){
        return "001";
    }

    public void ecgUplaod(CartaGrupo carta){
        try {
            StorageReference storage = ConfiguracaoFirebase.getFirebaseStorage();
            Bundle dados = getIntent().getExtras();
            final StorageReference salvararquivos = storage
                    .child(preferences.getString("email", "EMAILVAZIO"))
                    .child(preferences.getString("nomeBaralho", "pasta"))
                    .child(dados.getString("conjunto"))
                    .child(dados.getString("grupo"))
                    .child(carta.getNome())
                    .child("frenteAudio.mp3");
            UploadTask uploadTask = salvararquivos.putFile(Uri.parse(carta.getEndLocal()));
            uploadTask.addOnSuccessListener(
                    taskSnapshot -> salvararquivos.getDownloadUrl().addOnCompleteListener(
                            task -> {
                Uri endereco = task.getResult();
                assert endereco != null;
                carta.setEndWeb(endereco.toString());
                DatabaseReference firebase = ConfiguracaoFirebase.getDatabase();
                firebase.child(preferences.getString("email", "EMAILVAZIO"))
                        .child(preferences.getString("nomeBaralho", "pasta"))
                        .child("lista conjunto")
                        .child(preferences.getString("conjunto", ""))
                        .child("lista grupos")
                        .child(dados.getString("grupo"))
                        .child("lista cartas")
                        .child(carta.getNome())
                        .setValue(carta);
            })).addOnFailureListener(e -> {

            });
        }catch (Exception e){
            Log.i("FIReBASE", "ERRO: " + e);
        }
    }

}