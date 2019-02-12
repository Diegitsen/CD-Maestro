package com.example.cdmaestro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.List;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnosHolder>
{
    List<Alumno> alumnos;
    Context context;

    public AlumnoAdapter(List<Alumno> alumnos)
    {
        this.alumnos = alumnos;
    }


    @NonNull
    @Override
    public AlumnosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_asistencia,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Toast.makeText(v.getContext(), "Asistencia", Toast.LENGTH_SHORT).show();

                Fragment frag = new Asistencia2Fragment();
                ((MainActivity)v.getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();*/

            }
        });

        return new AlumnosHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnosHolder holder, int position)
    {
        holder.tvNombreAlumno.setText(alumnos.get(position).getNombre().toString());
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public class AlumnosHolder extends RecyclerView.ViewHolder {

        TextView tvNombreAlumno;

        public AlumnosHolder(View itemView) {
            super(itemView);
            tvNombreAlumno= (TextView) itemView.findViewById(R.id.tvNombreAlumno);

        }
    }
}
