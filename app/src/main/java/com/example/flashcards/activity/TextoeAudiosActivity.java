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
import com.example.flashcards.model.Carta;
import com.example.flashcards.model.CartaGrupo;
import com.example.flashcards.model.Conjunto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import android.annotation.SuppressLint;
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

import static com.example.flashcards.R.color.azul_claro;


public class TextoeAudiosActivity extends AppCompatActivity {
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
    private int proximo;
    private int proximo2;
    private Button avancar;
    private String textoParaEstudar;
    private boolean aux;
    private boolean booleanAux = true;
    private boolean ulttimoparagrafo;
    private final List<String> listaDeParagrafos = new ArrayList<>();//
    private final List<Conjunto> listaDeTextos = new ArrayList<>();//todo ok

    private final List<String> listaDeAudiosTexto = new ArrayList<>();
    private final List<String> listaDeAudiosTextoAux = new ArrayList<>();
    private final List<String> listaDeAudiosParagrafos = new ArrayList<>();
    private final List<String> listaDeTextosFrente = new ArrayList<>();
    private final List<String> listaDetextosVerso = new ArrayList<>();

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
        listaDeAudiosTexto.clear();
        funcao = 0;
        proximo  = 0;
        proximo2  = 0;
        ulttimoparagrafo = false;
        avancar = findViewById(R.id.tata_btn_avancar);
        preferences = getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("TA_aux", "sem_texto");
        editor.putString("TA_aux_P", "sem_texto");
        editor.apply();
        aux = true;

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
            case 6:
                funcaoSeis();
                break;
            case 7:
                funcaoSete();
                break;
            case 8:
                funcaoOito();
        }
        avancar.setEnabled(true);
    }

    public void funcaoZero() {
        while (aux) {
            qualTexto();
            funcao = 1;
            funcaoUm();
            funcaoDois();
            if(listaDeTextos.size()>0){
                aux = false;
            }
        }
    }
    public void funcaoUm() {
        tataListarParagrafos();
        if (listaDeParagrafos.size()>0){
            qualParagrafo();
            funcao = 2;
        }
        avancar.setEnabled(true);
    }
    public void funcaoDois(){
        //Todo fazer as listas
        booleanAux = true;
        for(int i = 0; i < listaDeParagrafos.size(); i++) {
            if(booleanAux) {criarListas(listaDeParagrafos.get(i));}
            if(listaDeParagrafos.get(i).equals(preferences.getString("TA_aux_P","falso"))){
                listaDeAudiosTexto.addAll(listaDeAudiosTextoAux);
                booleanAux = false;
                funcao = 3;
            }
        }
        avancar.setEnabled(true);
    }
    public void funcaoTres(){
        for(int i = 0; i < listaDeAudiosTexto.size();i++){
            //todo tocar audios;
            Log.i("MARCELO LAT", listaDeAudiosParagrafos.get(i) + "");
        }
        funcao = 4;
        avancar.setEnabled(true);
    }
    public void funcaoQuatro(){
        if (proximo < listaDeTextosFrente.size()){
            texto1.setText(listaDeTextosFrente.get(proximo));
            texto2.setText(listaDetextosVerso.get(proximo));
            //todo: Log.i("MARCELO LAP", listaDeAudiosParagrafos.size() + "");
            proximo++;
        }else {
            funcao = 5;
            proximo = 0;
            texto1.setText("");
            texto2.setText("");
        }
    }
    public void funcaoCinco(){

        //todo : colocar essa função dentro de um for(int i = 0; i < listaDeTextosFrente.size(); i ++)
        if (proximo < listaDeTextosFrente.size()){
            texto1.setText(listaDeTextosFrente.get(proximo));
            texto2.setText(listaDetextosVerso.get(proximo));
            texto1.setTextSize(30);
            texto2.setTextSize(30);

            //todo: Log.i("MARCELO LAP", listaDeAudiosParagrafos.size() + "");
        }else{
            texto1.setText("");
            texto2.setText("");
            texto1.setTextSize(20);
            texto2.setTextSize(20);
            funcao = 6;
        }
        if (proximo + 1 < listaDeTextosFrente.size()){
            texto3.setText(listaDeTextosFrente.get(proximo + 1));
            texto4.setText(listaDetextosVerso.get(proximo + 1));
            texto3.setTextSize(15);
            texto4.setTextSize(15);
        }else{
            texto3.setText("");
            texto4.setText("");

        }
        if (proximo  + 2 < listaDeTextosFrente.size()){
            texto5.setText(listaDeTextosFrente.get(proximo + 2));
            texto6.setText(listaDetextosVerso.get(proximo + 2));
            texto5.setTextSize(12);
            texto6.setTextSize(12);
        }else{
            texto5.setText("");
            texto6.setText("");

        }
        if (proximo  + 3 < listaDeTextosFrente.size()){
            texto7.setText(listaDeTextosFrente.get(proximo + 3));
            texto8.setText(listaDetextosVerso.get(proximo + 3));
            texto7.setTextSize(8);
            texto8.setTextSize(8);
        }else{
            texto7.setText("");
            texto8.setText("");

        }
        proximo ++;
    }
    private void funcaoSeis() {
        //todo : colocar essa função dentro de um for(int i = 0; i < listaDeTextosFrente.size(); i ++)
        if (proximo2 < listaDeTextosFrente.size()){
            texto1.setText(listaDeTextosFrente.get(proximo2));
            texto1.setTextSize(30);
            //todo: Log.i("MARCELO LAP", listaDeAudiosParagrafos.size() + "");
        }else{
            texto1.setText("");
            texto1.setTextSize(20);
            funcao = 7;
        }
        if (proximo2 + 1 < listaDeTextosFrente.size()){
            texto2.setText(listaDeTextosFrente.get(proximo2 + 1));
            texto2.setTextSize(15);
        }else{
            texto2.setText("");
        }

        if (proximo2 + 2 < listaDeTextosFrente.size()){
            texto3.setText(listaDeTextosFrente.get(proximo2 + 2));
            texto3.setTextSize(12);
        }else{
            texto3.setText("");
        }

        if (proximo2 + 3 < listaDeTextosFrente.size()){
            texto3.setText(listaDeTextosFrente.get(proximo2 + 3));
            texto3.setTextSize(10);
        }else{
            texto3.setText("");
        }

        if (proximo2 + 4 < listaDeTextosFrente.size()){
            texto4.setText(listaDeTextosFrente.get(proximo2 + 4));
            texto4.setTextSize(8);
        }else{
            texto4.setText("");
        }

        if (proximo2 + 5 < listaDeTextosFrente.size()){
            texto5.setText(listaDeTextosFrente.get(proximo2 + 5));
            texto5.setTextSize(6);
        }else{
            texto5.setText("");
        }

        if (proximo2 + 6 < listaDeTextosFrente.size()){
            texto6.setText(listaDeTextosFrente.get(proximo2 + 6));
            texto6.setTextSize(6);
        }else{
            texto6.setText("");
        }

        if (proximo2 + 7 < listaDeTextosFrente.size()){
            texto7.setText(listaDeTextosFrente.get(proximo2 + 7));
            texto7.setTextSize(6);
        }else{
            texto7.setText("");
        }

        if (proximo2 + 8 < listaDeTextosFrente.size()){
            texto8.setText(listaDeTextosFrente.get(proximo2 + 8));
            texto5.setTextSize(6);
        }else{
            texto8.setText("");
        }
        proximo2++;
    }
    public void funcaoSete(){
        for(int i = 0; i < listaDeAudiosTexto.size(); i++){
            //todo tocar audios;
            Log.i("MARCELO LAT", listaDeAudiosParagrafos.get(i) + "");
        }
        funcao = 8;
        avancar.setEnabled(true);
    }
    private void funcaoOito() {
        String confere = preferences.getString("TA_aux_P", "ERRO");
        String confereUltimoP = listaDeParagrafos.get(listaDeParagrafos.size()-1);
        for(int i = 0; i < listaDeTextosFrente.size(); i++ ){
            Carta carta = new Carta(preferences.getString("nomeBaralho", "erro"));
            carta.setDias(0);
            carta.setOrdem(0);
            carta.setMultiplicador(0);
            carta.setTextoFrente(listaDeTextosFrente.get(i));
            carta.setTextoVerso(listaDetextosVerso.get(i));
            carta.setEndAudioFrente(listaDeAudiosParagrafos.get(i));
            database.child(preferences.getString("email", "erro"))
                    .child(preferences.getString("nomeBaralho", "erro"))
                    .child("listaCartas")
                    .child(carta.getIdentificador())
                    .setValue(carta);
        }
        AuxiliarConjunto auxiliarConjunto = new AuxiliarConjunto(true);
        database.child(preferences.getString("email", "erro"))
                .child(preferences.getString("nomeBaralho", "erro"))
                .child("lista conjunto")
                .child(preferences.getString("conjunto", "ERRO"))
                .child("lista grupos")
                .child(preferences.getString("TA_aux_P", "ERRO"))
                .child("termino")
                .setValue(auxiliarConjunto);
        if(confere.equals(confereUltimoP)){
            ulttimoparagrafo = true;
        }
        if (ulttimoparagrafo){
            database.child(preferences.getString("email", "erro"))
                    .child(preferences.getString("nomeBaralho", "erro"))
                    .child("lista conjunto")
                    .child(preferences.getString("conjunto", "ERRO"))
                    .child("termino")
                    .setValue(auxiliarConjunto);
        }
        //TODO fazer o if para também fazer o true para o conjunto se este for o último parágrafo
        funcaoX();
    }
    private void criarListas(String s) {
        //TODO Estou trabalhando aqui
        valueEventListenerUsuario = usuario
                .child(preferences.getString("TA_aux", "ERRRO"))
                .child("lista grupos")
                .child(s)
                .child("lista cartas")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeAudiosParagrafos.clear();
                listaDeTextosFrente.clear();
                listaDetextosVerso.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    CartaGrupo carta = dados.getValue(CartaGrupo.class);
                    listaDeAudiosTextoAux.add(carta.getEndWeb());
                    listaDeAudiosParagrafos.add(carta.getEndWeb());
                    listaDeTextosFrente.add(carta.getFrente());
                    listaDetextosVerso.add(carta.getVerso());

                    Log.i("MARCELO LAT", carta.getEndWeb() + "");
                    Log.i("MARCELO LAP", carta.getEndWeb() + "");
                    Log.i("MARCELO LTF", carta.getFrente() + "");
                    Log.i("MARCELO LTV", carta.getVerso() + "");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });


    }
    public void funcaoX(){
        Intent intent = new Intent(TextoeAudiosActivity.this, CartaActivity.class);
        startActivity(intent);
        finish();
    }


    public void tataListarParagrafos(){
        Log.i("Marcelo Salvou",preferences.getString("TA_aux", "ERRRO"));
        valueEventListenerUsuario = usuario.child(preferences.getString("TA_aux", "ERRRO")).child("lista grupos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaDeParagrafos.clear();
                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Conjunto baralho = dados.getValue(Conjunto.class);
                    listaDeParagrafos.add(baralho.getNome());
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
                            Log.i("Marcelo Entrada",algo);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("TA_aux", algo);
                            editor.apply();
                            booleanAux = false;
                        }
                    }
                }
            }
        });
    }

    private void qualParagrafo() {
        booleanAux = true;
        for(int i = 0; i < listaDeParagrafos.size(); i++){
            pegaParagrafo(listaDeParagrafos.get(i));
        }
    }

    private void pegaParagrafo(String s) {
        usuario.child(preferences.getString("TA_aux", "ERRO"))
                .child("lista grupos")
                .child(s)
                .child("termino")
                .get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String agoravai = String.valueOf(task.getResult().getValue());
                    if (agoravai.equals("{termino=false}")){
                        if(booleanAux) {
                            Log.i("Marcelo paragrafo",s);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("TA_aux_P", s);
                            editor.apply();
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






