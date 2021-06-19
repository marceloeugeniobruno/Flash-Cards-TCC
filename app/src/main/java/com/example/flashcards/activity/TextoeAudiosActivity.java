package com.example.flashcards.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;
import com.example.flashcards.adapter.AdapterPrincipal;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.example.flashcards.model.AuxiliarConjunto;
import com.example.flashcards.model.Baralho;
import com.example.flashcards.model.Conjunto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class TextoeAudiosActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
    private final DatabaseReference database = ConfiguracaoFirebase.getDatabase();
    private DatabaseReference usuario;
    private ValueEventListener valueEventListenerUsuario;
    private SharedPreferences preferences;


    private TextView texto1;
    private TextView texto2;
    private TextView texto3;
    private TextView texto4;
    private TextView texto5;
    private TextView texto6;
    private TextView texto7;
    private TextView texto8;
    private int funcao;
    private Button avancar;
    private String textoParaEstudar;
    private String aux;
    private boolean booleanAux = true;
    private final List<Conjunto> listaParaaudios = new ArrayList<>();
    private final List<Conjunto> listaDeTextos = new ArrayList<>();
    private final List <AuxiliarConjunto> ax = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textoe_audios);

        texto1 = findViewById(R.id.tata_tx_1);
        texto2 = findViewById(R.id.tata_tx_2);
        texto3 = findViewById(R.id.tata_tx_3);
        texto4 = findViewById(R.id.tata_tx_4);
        texto5 = findViewById(R.id.tata_tx_5);
        texto6 = findViewById(R.id.tata_tx_6);
        texto7 = findViewById(R.id.tata_tx_7);
        texto8 = findViewById(R.id.tata_tx_8);
        funcao = 0;
        avancar = findViewById(R.id.tata_btn_avancar);
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);

        usuario = database
                .child(preferences.getString("email",""))
                .child(preferences.getString("nomeBaralho", ""))
                .child("lista conjunto");
        tataListarConjunto();
    }

    public void tataAvancar(View view){


        avancar.setEnabled(false);
        switch (funcao) {
            case 0:
                funcaoZero();
                break;
            case 1:
                funcaoUm();
                break;
            case 2:
                funcaoDois();
                break;
            case 3:
                funcaoTres();
                break;
            case 4:
                funcaoQuatro();
                break;
            case 5:
                funcaoCinco();
                break;
        }
        avancar.setEnabled(true);
    }

    public void funcaoZero() {

        qualTexto();
        qualParagrafo();
        //tataListarParagrafos();
        //tataTocarAudios();
        funcao = 1;
    }

    private void qualParagrafo() {
    }

    public void funcaoUm() {
        //TODO: pegar uma frase da fila e mostrar na tx 1 o texto em ingles (frente da carta),
        //TODO: e colocar na tx_2 o verso da carta
        //TODO:executar o áudio e salvar o nome do paragrafo para que os botões de audio saiba o áudio
        //TODO:qual é o arquivo de áudio
        //TODO:marca a frase como true, e Verificar se é a ultima carta da fila: se sim, segue para
        //TODO:a proxima fase: Se não função

        avancar.setEnabled(true);
    }
    public void funcaoDois(){
        //TODO: como o marcador está true vamos utillo de forma contrária
        //TODO: pegar uma frase da fila e mostrar na tx_1 o texto em ingles (frente da carta),
        //TODO: e colocar na tx_2 o verso da carta (verso texto)
        //TODO: pegar a próxima carta da fila frase da fila e mostrar na tx_3 o texto em ingles (frente da carta),
        //TODO: e colocar na tx_4 o verso da carta (verso texto)
        //TODO: pegar a próxima carta da fila frase da fila e mostrar na tx_5 o texto em ingles (frente da carta),
        //TODO: e colocar na tx_6 o verso da carta (verso texto)
        //TODO: pegar a próxima carta da fila frase da fila e mostrar na tx_7 o texto em ingles (frente da carta),
        //TODO: e colocar na tx_8 o verso da carta (verso texto)
        //TODO: Negritar o TX_1 e o TX_2 e tocar o áudio da frase da TX_1

        //TODO: Trocar a frase da tx_1 pela frase da tx_3 / a tx_2 pl tx_4 .... e para a TX_7 e TX_8 pegar a proxima frase se tiver
        //TODO: fazer essse processo até que o último áudio do texto seja tocado
        //TODO: sempre colocar um dalay entre a troca de áudios

        avancar.setEnabled(true);
    }
    public void funcaoTres(){
        //TODO: semelhante  função dois, mas somente com os textos (frente)
        funcao = 4;
        avancar.setEnabled(true);
    }
    public void funcaoQuatro(){
        //TODO: igual a função zero
        //TODO: transforma todas as frases em cartas para o baralho.
        Intent intent = new Intent(TextoeAudiosActivity.this, CartaActivity.class);
        startActivity(intent);
        finish();
    }
    public void funcaoCinco(){
        //TODO: fazer um alert dialog informando para o usuário que não há mais textos para estudar
        Intent intent = new Intent(TextoeAudiosActivity.this, CartaActivity.class);
        startActivity(intent);
        finish();

    }
    public void salvarCartas(){
        //TODO: Salvar as cartas na lista de cartas do baralho
    }
    public void tataListarParagrafos(){
        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaDeTextos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Conjunto baralho = dados.getValue(Conjunto.class);
                    listaDeTextos.add(baralho);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    public void tataListarConjunto(){
        // Read from the database
        valueEventListenerUsuario = usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listaDeTextos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Conjunto baralho = dados.getValue(Conjunto.class);
                    listaDeTextos.add(baralho);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    public void qualTexto(){
        for(int i = 0; i < listaDeTextos.size(); i++){
            pegaTexto(listaDeTextos.get(i).getNome());
        }
    }

    public void pegaTexto(String algo){
        final String[] teste = {"teste"};
        usuario.child(algo).child("termino").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String agoravai = String.valueOf(task.getResult().getValue());
                    if (agoravai.equals("{termino=false}")){
                        if(booleanAux) {
                            textoParaEstudar = algo;
                            booleanAux = false;
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        usuario.removeEventListener(valueEventListenerUsuario);
    }
}






