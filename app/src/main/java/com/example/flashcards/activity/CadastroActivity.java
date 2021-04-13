package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
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

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        campoNome = findViewById(R.id.cad_edt_nome);
        campoEmail = findViewById(R.id.cad_edt_email);
        campoSenha = findViewById(R.id.cad_edt_senha);
    }

    public void cadastroFinalizar(View view){
        finish();
    }

    public void cadastrarUsuario(Usuario usuario){
        autenticacao = ConfiguracaoFirebase.getAuth();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,
                            R.string.cad_msg_sucesso,
                            Toast.LENGTH_LONG).show();
                    finish();

                }else{
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

                    Toast.makeText(CadastroActivity.this,
                            execao,
                            Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    public void validarCadastroUsuario(View view){
        //recuperando textos
        String nome = campoNome.getText().toString();
        String email = campoEmail.getText().toString();
        String senha = campoSenha.getText().toString();
        if(!nome.isEmpty()){
            if(!email.isEmpty()){
                if(!senha.isEmpty()){
                    Usuario usuario = new Usuario();
                    usuario.setNome(nome);
                    usuario.setEmail(email);
                    usuario.setSenha(senha);
                    cadastrarUsuario(usuario);

                }else{
                    Toast.makeText(CadastroActivity.this,
                            R.string.cad_msg_senhanaopreechido,
                            Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(CadastroActivity.this,
                        R.string.cad_msg_emailnaopreechido,
                        Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(CadastroActivity.this,
                    R.string.cad_msg_nomenaopreechido,
                    Toast.LENGTH_LONG).show();
        }
    }
}