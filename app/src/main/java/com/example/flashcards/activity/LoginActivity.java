package com.example.flashcards.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.model.Usuario;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        campoEmail = findViewById(R.id.log_edt_email);
        campoSenha = findViewById(R.id.log_edt_senha);
    }
    public void esquiciSenha(View view){
        Intent esqueci = new Intent(getApplicationContext(), EsqueciSenhaActivity.class);
        startActivity(esqueci);
    }

    public void loginFinalizar(View view){
        finish();
    }


    public void validarLoginUsuario(View view){
        //recuperando textos
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();
        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                 Usuario usuario = new Usuario();
                 usuario.setEmail(email);
                 usuario.setSenha(senha);
            }else{
                 Toast.makeText(LoginActivity.this,
                       R.string.cad_msg_senhanaopreechido,
                       Toast.LENGTH_LONG).show();
            }
        }else{
                Toast.makeText(LoginActivity.this,
                        R.string.cad_msg_emailnaopreechido,
                        Toast.LENGTH_LONG).show();
        }

    }
}

