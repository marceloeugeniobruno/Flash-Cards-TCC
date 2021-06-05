package com.example.flashcards.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flashcards.R;
import com.example.flashcards.activity.EditorCartaGrupoActivity;
import com.example.flashcards.activity.EditorDeGruposActivity;
import com.example.flashcards.model.CartaGrupo;

import java.util.List;

public class AdapterEditorGrupo extends RecyclerView.Adapter<AdapterEditorGrupo.EditGrupoViewHolder> {
    Context context;
    List<CartaGrupo> grupoList;
    String conjunto;
    String grupo;

    public AdapterEditorGrupo(Context context, List<CartaGrupo> grupoList, String conjunto, String grupo) {
        this.context = context;
        this.grupoList = grupoList;
        this.conjunto = conjunto;
        this.grupo = grupo;
    }

    @NonNull
    @Override
    public AdapterEditorGrupo.EditGrupoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista_editor_baralho, parent, false);

        return new AdapterEditorGrupo.EditGrupoViewHolder(itemLista, context, conjunto, grupo);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEditorGrupo.EditGrupoViewHolder holder, int position) {
        CartaGrupo cartaGrupo = grupoList.get(position);
        holder.cartaFrente.setText(cartaGrupo.getFrente());
        holder.cartaVerso.setText(cartaGrupo.getVerso());
        holder.nome.setText(cartaGrupo.getNome());
        holder.endWeb.setText(cartaGrupo.getEndWeb());
        holder.endLocal.setText(cartaGrupo.getEndLocal());
    }

    @Override
    public int getItemCount() {
        return grupoList.size();
    }

    public static class EditGrupoViewHolder extends RecyclerView.ViewHolder{
        public TextView cartaFrente;
        public TextView cartaVerso;
        public TextView endWeb;
        public TextView endLocal;
        public TextView nome;

        public EditGrupoViewHolder(@NonNull  View itemView, Context context, String conjunto, String grupo) {
            super(itemView);
            cartaFrente = itemView.findViewById(R.id.edb_txt_frente);
            cartaVerso = itemView.findViewById(R.id.edb_txt_verso);
            endWeb = itemView.findViewById(R.id.edb_txt_audiof);
            endLocal = itemView.findViewById(R.id.edb_txt_audiov);
            nome = itemView.findViewById(R.id.edb_txt_id);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent irGrupo = new Intent(context, EditorCartaGrupoActivity.class);
                    irGrupo.putExtra("conjunto", conjunto);
                    irGrupo.putExtra("grupo", grupo);
                    irGrupo.putExtra("numerocarta", nome.getText().toString());
                    irGrupo.putExtra("frente", cartaFrente.getText().toString());
                    irGrupo.putExtra("verso", cartaVerso.getText().toString());
                    irGrupo.putExtra("elocal", endLocal.getText().toString());
                    irGrupo.putExtra("eweb", endWeb.getText().toString());
                    context.startActivity(irGrupo);
                }
            });


        }
    }


}
