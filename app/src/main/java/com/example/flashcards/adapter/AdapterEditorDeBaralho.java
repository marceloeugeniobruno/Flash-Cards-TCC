package com.example.flashcards.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;
import com.example.flashcards.activity.BaralhoComumActivity;
import com.example.flashcards.activity.BaralhoIdiomasActivity;
import com.example.flashcards.model.Baralho;
import com.example.flashcards.model.Carta;

import java.util.List;

public class AdapterEditorDeBaralho extends RecyclerView.Adapter<AdapterEditorDeBaralho.EditorBararalhoViewHolder> {

    Context context;
    List<Carta> cartaList;

    public AdapterEditorDeBaralho(List<Carta> cartaLista, Context context) {
        this.context = context;
        this.cartaList = cartaLista;
    }

    @NonNull
    @Override
    public EditorBararalhoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_editor_baralho, parent, false);

        return new EditorBararalhoViewHolder(itemLista , context);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEditorDeBaralho.EditorBararalhoViewHolder holder, int position) {
        Carta carta = cartaList.get(position);
        holder.textoCartaFrente.setText(carta.getTextoFrente());;
        holder.textoCartaVerso.setText(carta.getTextoVerso());

    }

    @Override
    public int getItemCount() {
        return cartaList.size();
    }


    public static class EditorBararalhoViewHolder extends RecyclerView.ViewHolder {
        TextView textoCartaFrente;
        TextView textoCartaVerso;

        public EditorBararalhoViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            textoCartaFrente = itemView.findViewById(R.id.edb_txt_frente);
            textoCartaVerso = itemView.findViewById(R.id.edb_txt_verso);
        }
    }


}
