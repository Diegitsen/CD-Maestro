package com.example.cdmaestro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AlumnoNotaAdapter extends RecyclerView.Adapter<AlumnoNotaAdapter.AlumnosNotasHolder>
{

    List<Alumno> alumnos;
    Context context;

    public AlumnoNotaAdapter(List<Alumno> alumnos)
    {
        this.alumnos = alumnos;
    }

    @NonNull
    @Override
    public AlumnosNotasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        return new AlumnosNotasHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoNotaAdapter.AlumnosNotasHolder holder, int position) {

        holder.tvNombreAlumno.setText(alumnos.get(position).getNombre().toString());

    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public class AlumnosNotasHolder extends RecyclerView.ViewHolder {

        TextView tvNombreAlumno;


        public AlumnosNotasHolder(View itemView) {
            super(itemView);

            tvNombreAlumno= (TextView) itemView.findViewById(R.id.tvNombreAlumno);

        }
    }
}
