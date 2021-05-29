package com.example.flashcards.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;
import com.example.flashcards.activity.BaralhoComumActivity;
import com.example.flashcards.activity.BaralhoIdiomasActivity;
import com.example.flashcards.activity.EditorDeCartasActivity;
import com.example.flashcards.activity.MainActivity;
import com.example.flashcards.config.ConfiguracaoFirebase;
import com.example.flashcards.helper.Base64Custon;
import com.example.flashcards.model.Baralho;
import com.example.flashcards.model.Carta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterEditorDeBaralho extends RecyclerView.Adapter<AdapterEditorDeBaralho.EditorBararalhoViewHolder> {

    Context context;
    List<Carta> cartaList;
    Carta carta;
    String nomeBaralho;

    public AdapterEditorDeBaralho(List<Carta> cartaLista, Context context, String nomeBaralho) {
        this.context = context;
        this.cartaList = cartaLista;
        this.nomeBaralho = nomeBaralho;

    }
    @NonNull
    @Override
    public EditorBararalhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_editor_baralho, parent, false);
        SharedPreferences preferences = context.getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
        return new EditorBararalhoViewHolder(itemLista , context, preferences, nomeBaralho);
    }


    @Override
    public void onBindViewHolder(@NonNull AdapterEditorDeBaralho.EditorBararalhoViewHolder holder, int position) {
        carta = cartaList.get(position);
        holder.textoCartaFrente.setText(carta.getTextoFrente());;
        holder.textoCartaVerso.setText(carta.getTextoVerso());
        holder.textoEndAuF.setText(carta.getEndAudioFrente());
        holder.textoEndauV.setText(carta.getEndAudioVerso());
        holder.textoid.setText(carta.getIdentificador());

    }

    @Override
    public int getItemCount() {
        return cartaList.size();
    }


    public static class EditorBararalhoViewHolder extends RecyclerView.ViewHolder {
        TextView textoCartaFrente;
        TextView textoCartaVerso;
        TextView textoEndAuF;
        TextView textoEndauV;
        TextView textoid;
        TextView nome;

        public EditorBararalhoViewHolder(@NonNull View itemView, Context context, SharedPreferences preferences, String nomebaralho) {
            super(itemView);
            textoCartaFrente = itemView.findViewById(R.id.edb_txt_frente);
            textoCartaVerso = itemView.findViewById(R.id.edb_txt_verso);
            textoEndAuF = itemView.findViewById(R.id.edb_txt_audiof);
            textoEndauV = itemView.findViewById(R.id.edb_txt_audiov);
            textoid = itemView.findViewById(R.id.edb_txt_id);
            nome = itemView.findViewById(R.id.edb_txt_nomeb);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //instanciar alertdialog
                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    //configurar tipo mensagem
                    dialog.setTitle(R.string.edb_dia_titulo2);
                    dialog.setMessage(R.string.edb_dia_mensage2);
                    //configurar ações
                    dialog.setPositiveButton(R.string.edb_dia_editar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = preferences.edit();
                            //flags da carta frente
                            editor.putString("textoFrente",textoCartaFrente.getText().toString());
                            editor.putString("endAF",textoEndAuF.getText().toString());;
                            editor.putString("textoVerso",textoCartaVerso.getText().toString());
                            editor.putString("endAV",textoEndauV.getText().toString());
                            editor.putString("identificador",textoid.getText().toString());
                            editor.apply();
                            Intent irCarta = new Intent(context, EditorDeCartasActivity.class);
                            irCarta.putExtra("nomeBaralho",nomebaralho);
                            irCarta.putExtra("flag",3);
                            context.startActivity(irCarta);

                        }
                    });
                    dialog.setNegativeButton(R.string.edb_dia_excluir, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder dialog2 = new AlertDialog.Builder(context);
                            dialog2.setTitle(R.string.edb_dia_excluir);
                            dialog2.setMessage(R.string.edb_dia_mensage3);
                            dialog2.setPositiveButton(R.string.edb_dia_sim, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    StorageReference storageReference = ConfiguracaoFirebase.getFirebaseStorage();
                                    String af = textoEndAuF.getText().toString();
                                    String av = textoEndauV.getText().toString();

                                    DatabaseReference firebase = ConfiguracaoFirebase.getDatabase();
                                    FirebaseAuth autenticacao = ConfiguracaoFirebase.getAuth();
                                    String email = Base64Custon.codificarBase64(autenticacao.getCurrentUser().getEmail());

                                    if (!af.equals("")){
                                        StorageReference arquivo = storageReference
                                                .child(email)
                                                .child(nomebaralho)
                                                .child(textoid.getText().toString())
                                                .child("frenteAudio.mp3");
                                        arquivo.delete();
                                    }
                                    if (!av.equals("")){
                                        StorageReference arquivo = storageReference
                                                .child(email)
                                                .child(nomebaralho)
                                                .child(textoid.getText().toString())
                                                .child("versoAudio.mp3");
                                        arquivo.delete();
                                    }
                                    DatabaseReference cartaRef = ConfiguracaoFirebase.getDatabase();
                                    cartaRef.child(email)
                                            .child(nomebaralho)
                                            .child("listaCartas")
                                            .child(textoid.getText().toString())
                                            .removeValue();
                                }
                            });

                            dialog2.setNegativeButton(R.string.edb_dia_nao, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            dialog2.create();
                            dialog2.show();
                        }
                    });
                    //criar e exibir
                    dialog.create();
                    dialog.show();
                }
            });



        }
    }


}
