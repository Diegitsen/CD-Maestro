package com.example.cdmaestro;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class AlumnoNota2Adapter extends RecyclerView.Adapter<AlumnoNota2Adapter.AlumnosNotas2Holder>
{
    List<Alumno_Nota> alumnos_notas;

    public AlumnoNota2Adapter(List<Alumno_Nota> alumnos_notas)
    {
        this.alumnos_notas = alumnos_notas;
    }

    @NonNull
    @Override
    public AlumnosNotas2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);

        return new AlumnosNotas2Holder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnosNotas2Holder holder, int position) {


        Alumno_Nota alumno_nota = alumnos_notas.get(position);

        //holder.tvNombreAlumno.setText(alumno_nota.getNombre() + " " + alumno_nota.getApellido());
        holder.etNota.setText(alumno_nota.getNota()+"");

    }

    @Override
    public int getItemCount() {
        return alumnos_notas.size();
    }

    public class AlumnosNotas2Holder extends RecyclerView.ViewHolder {

        TextView tvNombreAlumno;
        EditText etNota;


        public AlumnosNotas2Holder(View itemView) {
            super(itemView);

            tvNombreAlumno= (TextView) itemView.findViewById(R.id.tvNombreAlumno);
            etNota = (EditText) itemView.findViewById(R.id.etNota);
        }
    }
}
