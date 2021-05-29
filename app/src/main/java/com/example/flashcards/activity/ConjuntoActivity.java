package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flashcards.R;
import com.example.flashcards.adapter.AdapterConjunto;
import com.example.flashcards.adapter.AdapterPrincipal;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.example.flashcards.model.Baralho;
import com.example.flashcards.model.Conjunto;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConjuntoActivity extends AppCompatActivity {

    private DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerUsuario;
    private List<Conjunto> listaDeConjuntos = new ArrayList<>();
    private AdapterConjunto adapterConjunto;
    private SharedPreferences preferences;
    private EditText nomeConj;
    private Button criarconj;
    private Button cancelar;
    //private List<Conjunto> listaDeConj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conjunto);
        nomeConj = findViewById(R.id.conj_edt_nome);
        criarconj = findViewById(R.id.conj_btn_criar);
        cancelar = findViewById(R.id.conj_btn_cancelar);
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
    }

    public void listarconjunto(){


        RecyclerView recyclerView = findViewById(R.id.recycler_conj);

        usuario = database
                .child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto");

        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeConjuntos.clear();
                for(DataSnapshot dados: snapshot.getChildren()){
                    try {
                        Conjunto conjunto = dados.getValue(Conjunto.class);
                        listaDeConjuntos.add(conjunto);
                    }catch (Exception e){
                        Log.i("flashcards", "va se ferrar marcelo");
                    }
                }
                List<Conjunto> listaDeConjuntosAux = new ArrayList<>();
                /*
                ordenando a lista por ordem de excecução
                 */
                for (int i = 0; i < listaDeConjuntos.size(); i++){
                    for(int j = 0; j < listaDeConjuntos.size(); j ++ ){
                        if(i == listaDeConjuntos.get(j).getOrdem()){
                            listaDeConjuntosAux.add(listaDeConjuntos.get(j));
                            j = listaDeConjuntos.size();
                        }
                    }
                }
                listaDeConjuntos = listaDeConjuntosAux;
                adapterConjunto.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO: Fazer tratamento de errro
            }
        });
        //configuração do adapterLog.i("marcelo1", listaDeConjuntos.get(0).getNome());
        adapterConjunto = new AdapterConjunto(listaDeConjuntos, this);

        //configuração da Recyclerview
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterConjunto);
    }

    public void mostrarBotoes(View view){
        nomeConj.setVisibility(View.VISIBLE);
        criarconj.setVisibility(View.VISIBLE);
        cancelar.setVisibility(View.VISIBLE);

    }

    public void ocultarBotoes(View view){
        oBtn();
    }

    public void oBtn(){
        nomeConj.setVisibility(View.INVISIBLE);
        criarconj.setVisibility(View.INVISIBLE);
        cancelar.setVisibility(View.INVISIBLE);
    }

    public void criarConjunto(View view){
        String nome = nomeConj.getText().toString();

        usuario = database
                .child(preferences.getString("email", ""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto");


        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Conjunto> conj = new ArrayList<>();
                int k = 0;
                for(DataSnapshot dados: snapshot.getChildren()){
                    try {
                        Conjunto conjunto = dados.getValue(Conjunto.class);
                        conj.add(conjunto);
                        k++;
                    }catch ( Exception e){
                        Log.i("flashCards", "marcelo, va se ferrar de novo");
                    };
                }
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("tamanho", k);
                editor.apply();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO: Fazer tratamento de errro
            }
        });

        if(!nome.equals("")) {
            Conjunto conjunto = new Conjunto();
            conjunto.setOrdem(preferences.getInt("tamanho", 0));
            String orden = "";
            if(conjunto.getOrdem()<9){
                orden = "00";
            }else if(conjunto.getOrdem()<99){
                orden = "0";
            }
            conjunto.setNome(orden + (conjunto.getOrdem() + 1) + " - "  + nome);
            conjunto.setTermino(false);
            database.child(preferences.getString("email", ""))
                    .child(preferences.getString("nomeBaralho", ""))
                    .child("lista conjunto")
                    .child(conjunto.getNome())
                    .setValue(conjunto);
            nomeConj.setText("");
            oBtn();
            Intent irGrupo = new Intent(ConjuntoActivity.this, GrupoActivity.class);
            irGrupo.putExtra("conjunto", conjunto.getNome());
            startActivity(irGrupo);
        }else {
            Toast.makeText(ConjuntoActivity.this,
                    R.string.conj_toast,
                    Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public void conjVoltar(View view){
        usuario.removeEventListener(valueEventListenerUsuario);
        finish();
    }

    public void explicacao(View view){
        String mensagem =
                "São textos inteiros, com diversos paráragrafos. Cada pararáfo formará um grupo, " +
                "e cada grupo é dividido em frases. Quando fores estudar, o programa irá mostrar " +
                "apenas um grupo, mas os audios do CONJUNTO serão todos executados, até o atual " +
                "grupo que está sendo estudado.\n\n" +
                "Após o termino do estudo de um grupo, todas as cartas contidas no grupo, farão " +
                "parte de seu baralho.\n\n" +
                "OBSERVAÇÃO: Após a criação do Conjunto, ele não poderá mais ser deletado e nem " +
                "renomeado. O que pode fazer é desablitar a execução do conjunto entrando no " +
                "conjunto. E lá terá um botão para desabilitar o conjunto";
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //configurar tipo mensagem
        dialog.setTitle(R.string.conj_titul);
        dialog.setMessage(mensagem);
        dialog.create();
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
        listarconjunto();
    }
}