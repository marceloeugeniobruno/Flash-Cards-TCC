package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.flashcards.R;
import com.example.flashcards.adapter.AdapterEditorDeBaralho;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.example.flashcards.model.Carta;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class EditorDeBaralhoActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private AdapterEditorDeBaralho adapterCarta;
    private String email;
    private DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private List<Carta> listaDeCartas = new ArrayList<>();
    private ValueEventListener valueEventListenerUsuario;
    String nomeBaralho;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor_de_baralho);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle dados = getIntent().getExtras();
        nomeBaralho = dados.getString("nomeBaralho");
        recyclerView = findViewById(R.id.edbc_lista_baralho);
        FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
        email = Base64Custon.codificarBase64(autenticacao.getCurrentUser().getEmail());
        //criação da lista
        usuario = database.child(email).child(nomeBaralho).child("listaCartas");
        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDeCartas.clear();
                for(DataSnapshot dados: snapshot.getChildren()){
                    Carta carta = dados.getValue(Carta.class);
                    listaDeCartas.add(carta);
                }
                adapterCarta.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO: Fazer tratamento de errro
            }
        });
        //configurar adapter
        adapterCarta = new AdapterEditorDeBaralho(listaDeCartas, this);
        //configurar recycleviw
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterCarta);
    }

    public void edbcVoltar(View view){
        finish();
    }

    public void confirmacao(){
        //instanciar alertdialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //configurar tipo mensagem
        dialog.setTitle(R.string.edb_dia_titulo);
        dialog.setMessage(R.string.edb_dia_confirmaçao);
        //configurar ações
        dialog.setPositiveButton(R.string.edb_dia_sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //exclui os dados do realtime datababese
                //todo: não está excuindo

                StorageReference storageReference = ConfiguracaoFirebase.getFirebaseStorage();
                for(Carta c: listaDeCartas){
                    if (!c.getEndAudioFrente().equals("")){
                        StorageReference arquivo = storageReference
                                .child(email)
                                .child(nomeBaralho)
                                .child(c.getIdentificador())
                                .child("frenteAudio.mp3");
                        arquivo.delete();
                    }
                    if (!c.getEndAudioVerso().equals("")){
                        StorageReference arquivo = storageReference
                                .child(email)
                                .child(nomeBaralho)
                                .child(c.getIdentificador())
                                .child("versoAudio.mp3");
                        arquivo.delete();
                    }

                }

                //TODO: criar loop para deletar arquivos do storarge
                database.child(email).child(nomeBaralho).removeValue();
                finish();
            }
        });
        dialog.setNegativeButton(R.string.edb_dia_nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //criar e exibir
        dialog.create();
        dialog.show();
    }

    public void edbcExcluir(View view){
        //instanciar alertdialog
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        //configurar tipo mensagem
        dialog.setTitle(R.string.edb_dia_titulo);
        dialog.setMessage(R.string.edb_dia_mensage);
        //configurar ações
        dialog.setPositiveButton(R.string.edb_dia_sim, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirmacao();
            }
        });
        dialog.setNegativeButton(R.string.edb_dia_nao, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //criar e exibir
        dialog.create();
        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuario.removeEventListener(valueEventListenerUsuario);
    }


}