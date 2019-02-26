package com.example.cdmaestro;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cdmaestro.Asistencia.Asistencia2Fragment;
import com.example.cdmaestro.Asistencia.AsistenciaFragment;
import com.example.cdmaestro.Info.Info2Fragment;
import com.example.cdmaestro.Notas.Notas2Fragment;
import com.example.cdmaestro.Utils.GlobalVars;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursosHolder>
{
    List<Curso> cursos;
    Context context;

    public CursoAdapter(List<Curso> cursos)
    {
        this.cursos = cursos;
    }



    @NonNull
    @Override
    public CursosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        return new CursosHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final CursosHolder holder, final int position)
    {
        holder.tvNombreCurso.setText(cursos.get(position).getNombre().toString());
        holder.tvTurno.setText(cursos.get(position).getTurno() + "");


        holder.lay_item_curso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment frag  = new Notas2Fragment();

                switch (GlobalVars.cursoFragment)
                {
                    case 1:
                         frag = new Asistencia2Fragment();
                        break;

                    case 2:
                         frag = new Notas2Fragment();
                        break;
                    case 3:
                        frag = new Info2Fragment();
                        break;
                }

                Bundle b = new Bundle();
                b.putInt("ID_CURSO", cursos.get(position).getIdCurso());
                frag.setArguments(b);

                ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
            }
        });

    }


    @Override
    public int getItemCount() {
        return cursos.size();
    }


    public class CursosHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCurso,tvTurno;
        LinearLayout lay_item_curso;

        public CursosHolder(View itemView) {
            super(itemView);
            tvNombreCurso= (TextView) itemView.findViewById(R.id.tvNombreCurso);
            tvTurno= (TextView) itemView.findViewById(R.id.tvTurno);
            lay_item_curso = itemView.findViewById(R.id.lay_item_curso);
        }
    }


}
