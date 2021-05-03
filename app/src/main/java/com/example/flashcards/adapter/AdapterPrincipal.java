package com.example.flashcards.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;
import com.example.flashcards.model.Baralho;

import java.util.List;

public class AdapterPrincipal extends RecyclerView.Adapter<AdapterPrincipal.PrincipalViewHolder> {
    Context context;
    List<Baralho> baralhoList;

    public AdapterPrincipal(List<Baralho> baralhoList, Context context) {
        this.context = context;
        this.baralhoList = baralhoList;
    }

    @NonNull
    @Override
    public PrincipalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_principal, parent, false);

        return new PrincipalViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull PrincipalViewHolder holder, int position) {
        Baralho baralho = baralhoList.get(position);
        holder.nomeBaralho.setText(baralho.getNome());
        holder.tipo.setText(baralho.getTipo());
    }

    @Override
    public int getItemCount() {

        return baralhoList.size();
    }

    public class PrincipalViewHolder extends RecyclerView.ViewHolder{
        TextView nomeBaralho;
        TextView tipo;

        public PrincipalViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeBaralho = itemView.findViewById(R.id.adp_pri_titulo);
            tipo = itemView.findViewById(R.id.adp_pri_tipo);

        }
    }

}
