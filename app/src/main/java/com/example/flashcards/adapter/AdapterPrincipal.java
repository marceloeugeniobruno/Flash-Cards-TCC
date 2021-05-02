package com.example.flashcards.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;

public class AdapterPrincipal extends RecyclerView.Adapter<AdapterPrincipal.PrincipalViewHolder> {

    @NonNull
    @Override
    public PrincipalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_principal, parent, false);

        return new PrincipalViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull PrincipalViewHolder holder, int position) {
        holder.nomeBaralho.setText("Nome do Baralho");
        holder.tipo.setText("tipo");

    }

    @Override
    public int getItemCount() {
        return 2;
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
