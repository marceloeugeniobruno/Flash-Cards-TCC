package com.example.flashcards.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.example.flashcards.activity.MainActivity;
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

        return new PrincipalViewHolder(itemLista, context);
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


    public static class PrincipalViewHolder extends RecyclerView.ViewHolder {
        TextView nomeBaralho;
        TextView tipo;

        public PrincipalViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            nomeBaralho = itemView.findViewById(R.id.adp_pri_titulo);
            tipo = itemView.findViewById(R.id.adp_pri_tipo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String confereTipo = tipo.getText().toString();
                    String nome = nomeBaralho.getText().toString();
                    if(confereTipo.equals("Idiomas")){
                        Intent irBaraComum = new Intent(context, BaralhoIdiomasActivity.class);
                        irBaraComum.putExtra("nomeBaralho", nome);
                        context.startActivity(irBaraComum);
                    }else{
                        Intent irBaraComum = new Intent(context, BaralhoComumActivity.class);
                        irBaraComum.putExtra("nomeBaralho", nome);
                        context.startActivity(irBaraComum);
                    }
                    Log.i("FIREBASE","Element " + nomeBaralho.getText() + " clicked.");
                }
            });


        }
    }

}
