package com.example.flashcards.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.flashcards.R;
import com.example.flashcards.activity.GrupoActivity;
import com.example.flashcards.activity.MainActivity;
import com.example.flashcards.model.Conjunto;

import java.util.List;

public class AdapterConjunto extends RecyclerView.Adapter<AdapterConjunto.ConjuntoViewHolder>{

    Context context;
    List<Conjunto> coonjuntoList;

    public AdapterConjunto(List<Conjunto> conjuntoList, Context context) {
        this.context = context;
        this.coonjuntoList = conjuntoList;
    }

    @NonNull
    @Override
    public AdapterConjunto.ConjuntoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_conjunto, parent, false);

        return new AdapterConjunto.ConjuntoViewHolder(itemLista, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterConjunto.ConjuntoViewHolder holder, int position) {
        Conjunto conjunto = coonjuntoList.get(position);
        holder.nomeConjunto.setText(conjunto.getNome());
    }

    @Override
    public int getItemCount() {
        return coonjuntoList.size();
    }

    public static class ConjuntoViewHolder extends RecyclerView.ViewHolder {
        TextView nomeConjunto;

        public ConjuntoViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            nomeConjunto = itemView.findViewById(R.id.adp_tex_conj);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = context.getSharedPreferences(MainActivity.ARQUIVO_PREFERENCIAS, 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    String nome = nomeConjunto.getText().toString();
                    Intent irGrupo = new Intent(context, GrupoActivity.class);
                    irGrupo.putExtra("conjunto", nome);
                    editor.putString("conjunto", nome);
                    editor.apply();
                    context.startActivity(irGrupo);
                }
            });

        }
    }
}
