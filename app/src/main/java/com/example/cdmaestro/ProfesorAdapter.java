package com.example.cdmaestro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ProfesorAdapter extends RecyclerView.Adapter<ProfesorAdapter.ProfesoresHolder>
{
    List<Profesor> profesores;

    public ProfesorAdapter(List<Profesor> profesores)
    {
        this.profesores = profesores;
    }

    @NonNull
    @Override
    public ProfesoresHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new ProfesoresHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfesoresHolder holder, int position)
    {
/*
        holder.tvNombreCurso.setText(profesores.get(position).getDocumento().toString());
        holder.txtNombre.setText(listaUsuarios.get(position).getNombre().toString());
        holder.txtProfesion.setText(listaUsuarios.get(position).getProfesion().toString());*/

    }

    @Override
    public int getItemCount() {
        return profesores.size();
    }

    public class ProfesoresHolder extends RecyclerView.ViewHolder {

        TextView tvNombreCurso,tvTurno;


        public ProfesoresHolder(View itemView)
        {
            super(itemView);

            tvNombreCurso= (TextView) itemView.findViewById(R.id.tvNombreCurso);
            tvTurno= (TextView) itemView.findViewById(R.id.tvTurno);
        }
    }
}
