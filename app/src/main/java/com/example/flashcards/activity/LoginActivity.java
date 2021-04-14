package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class LoginActivity extends AppCompatActivity {

    private EditText campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        autenticacao = ConfiguracaoFirebase.getAuth();
        campoEmail = findViewById(R.id.log_edt_email);
        campoSenha = findViewById(R.id.log_edt_senha);
    }
    public void esquiciSenha(View view){
        Intent esqueci = new Intent(getApplicationContext(), EsqueciSenhaActivity.class);
        startActivity(esqueci);
    }

    public void abrirtelaPrincipal(){
        Intent principal = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(principal);
    }

    public void loginFinalizar(View view){
        finish();
    }

    public void logarUsuario(Usuario usuario){
        autenticacao.signInWithEmailAndPassword(usuario.getEmail(),usuario.getSenha())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            abrirtelaPrincipal();
                            finish();

                        }else {
                            String execao = "";
                            try{
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e) {
                                execao = "digite uma senha mais forte!";
                            }catch (FirebaseAuthInvalidCredentialsException e) {
                                execao = "Por favor Digite um email valido!";
                            }catch (FirebaseAuthUserCollisionException e){
                                execao = "Usuário já cadastrado!";
                            } catch (Exception e) {
                                execao = "Erro ao cadastrar usuário!" + e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(LoginActivity.this,
                                    execao,
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
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
                 logarUsuario(usuario);
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

