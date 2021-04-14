package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.model.Usuario;

public class EsqueciSenhaActivity extends AppCompatActivity {
    private EditText campoEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esqueci_senha);
        campoEmail = findViewById(R.id.eqs_edt_email);
    }

    public void esqueciFinalizar(View view){
        finish();
    }

    public void sollicitarSenha(View view){
        //recuperando textos
        String email = campoEmail.getText().toString();
        if(!email.isEmpty()){

        }else{
            Toast.makeText(EsqueciSenhaActivity.this,
                    R.string.cad_msg_emailnaopreechido,
                    Toast.LENGTH_LONG).show();
        }

    }
}