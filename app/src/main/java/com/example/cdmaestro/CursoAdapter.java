package com.example.cdmaestro;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CursoAdapter extends RecyclerView.Adapter<CursoAdapter.CursosHolder>
{
    List<Curso> cursos;

    public CursoAdapter(List<Curso> cursos)
    {
        this.cursos = cursos;
    }



    @NonNull
    @Override
    public CursosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_curso,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new CursosHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull CursosHolder holder, int position)
    {
        holder.tvNombreCurso.setText(cursos.get(position).getNombre().toString());
        holder.tvTurno.setText(cursos.get(position).getTurno() + "");

    }

    @Override
    public int getItemCount() {
        return cursos.size();
    }


    public class CursosHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCurso,tvTurno;

        public CursosHolder(View itemView) {
            super(itemView);
            tvNombreCurso= (TextView) itemView.findViewById(R.id.tvNombreCurso);
            tvTurno= (TextView) itemView.findViewById(R.id.tvTurno);
        }
    }
}
