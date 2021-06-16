package com.example.flashcards.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;
import com.example.flashcards.activity.EditorDeGruposActivity;
import com.example.flashcards.activity.MainActivity;
import com.example.flashcards.model.Grupo;

import java.util.List;

public class AdapterGrupo extends RecyclerView.Adapter<AdapterGrupo.GrupoViewHolder> {
    Context context;
    List<Grupo> grupoList;
    String conjunto;

    public AdapterGrupo(Context context, List<Grupo> grupoList, String conjunto) {
        this.context = context;
        this.grupoList = grupoList;
        this.conjunto = conjunto;
    }

    @NonNull
    @Override
    public AdapterGrupo.GrupoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_conjunto, parent, false);

        return new AdapterGrupo.GrupoViewHolder(itemLista, context, conjunto);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterGrupo.GrupoViewHolder holder, int position) {
        Grupo grupo = grupoList.get(position);
        holder.nomeGrupo.setText(grupo.getNome());

    }

    @Override
    public int getItemCount() {
        return grupoList.size();
    }

    public static class GrupoViewHolder extends RecyclerView.ViewHolder{
        TextView nomeGrupo;

        public GrupoViewHolder(@NonNull View itemView, Context context, String conjunto) {
            super(itemView);
            nomeGrupo = itemView.findViewById(R.id.adp_tex_conj);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, conjunto,Toast.LENGTH_LONG).show();
                    String nome = nomeGrupo.getText().toString();
                    Intent irGrupo = new Intent(context, EditorDeGruposActivity.class);
                    irGrupo.putExtra("conjunto", conjunto);
                    irGrupo.putExtra("grupo", nome);
                    context.startActivity(irGrupo);
                }
            });



        }


    }
}
