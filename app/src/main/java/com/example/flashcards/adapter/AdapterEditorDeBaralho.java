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
import com.example.flashcards.model.Baralho;
import com.example.flashcards.model.Carta;

import java.util.List;

public class AdapterEditorDeBaralho extends RecyclerView.Adapter<AdapterEditorDeBaralho.EditorBararalhoViewHolder> {

    Context context;
    List<Carta> cartaList;
    Carta carta;
    String nomeBaralho;
    private SharedPreferences preferences;

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
        preferences = context.getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);

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
                            // confirmacao();
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
