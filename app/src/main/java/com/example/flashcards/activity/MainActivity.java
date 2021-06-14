package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.flashcards.R;
import com.example.flashcards.adapter.AdapterPrincipal;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.example.flashcards.model.Baralho;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public final static String ARQUIVO_PREFERENCIAS = "arquivoPreferencia";
    private String email;
    private final DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerUsuario;
    private final List<Baralho> listaDeBaralhos = new ArrayList<>();
    private AdapterPrincipal adapterPrincipal;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //criando dados para criação das cartas
        SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
        editor = preferences.edit();
        //variaveis da carta frente
        editor.putString("textoFrente","");
        editor.putString("endAF","");
        editor.putString("endIF","");
        editor.putString("endVF","");
        editor.putString("endAFWEB","");

        //variaveis da carta verso
        editor.putString("textoVerso","");
        editor.putString("endAV","");
        editor.putString("endIV","");
        editor.putString("endVV","");
        editor.putString("endAVWEB","");

        editor.putBoolean("deletado",false);
        editor.putString("nomeBaralho","");
        editor.putString("tipoBaralho","");

        editor.apply();
    }

    public void movimentacao(){
        try {
            RecyclerView recyclerView = findViewById(R.id.pri_rc);
            //criação da lista
            FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
            email = Base64Custon.codificarBase64(Objects.requireNonNull(Objects.requireNonNull(autenticacao.getCurrentUser()).getEmail()));
            editor.putString("email", email);
            editor.apply();
            usuario = database.child(email);
            valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    listaDeBaralhos.clear();
                    for (DataSnapshot dados : snapshot.getChildren()) {
                        Baralho baralho = dados.getValue(Baralho.class);
                        listaDeBaralhos.add(baralho);
                    }
                    adapterPrincipal.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //TODO: Fazer tratamento de errro
                }
            });
            //configuração do adapter
            adapterPrincipal = new AdapterPrincipal(listaDeBaralhos, this);
            //configuração da Recyclerview
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapterPrincipal);
        }catch (Exception e){
            Log.i("FIREBASE" , "Erro: " + e);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
        movimentacao();
    }

    public void verificarUsuarioLogado(){
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
        if(autenticacao.getCurrentUser() == null){
            Intent inicial = new Intent(getApplicationContext(), InicialActivity.class);
            startActivity(inicial);
        }else{
            email = Base64Custon.codificarBase64(Objects.requireNonNull(autenticacao.getCurrentUser().getEmail()));
            SharedPreferences preferences = getSharedPreferences(ARQUIVO_PREFERENCIAS, 0);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("emailBase64",email);
            editor.apply();
        }

    }

    public void criarcaodeBaralho(View view){
        Intent inicial = new Intent(getApplicationContext(), CriarBaralhoActivity.class);
        startActivity(inicial);
    }


    @Override
    protected void onStop() {
        super.onStop();
        try {
            usuario.removeEventListener(valueEventListenerUsuario);
        }catch (Exception e){
            Log.i("FIREBASE" , "Erro: " + e);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_deslogar:
                //TODO: Não esquecer de retirar os comentários das linhas abaixo ao finalizar o app
                //FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
                //autenticacao.signOut();
                Intent intent = new Intent(MainActivity.this, InicialActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_trocasenha:
                Intent intent2 = new Intent(MainActivity.this, TrocaDeSenhaMainActivity.class);
                intent2.putExtra("activite", "main");
                startActivity(intent2);
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}