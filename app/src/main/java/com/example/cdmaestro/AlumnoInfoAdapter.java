package com.example.cdmaestro;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AlumnoInfoAdapter extends RecyclerView.Adapter<AlumnoInfoAdapter.AlumnosInfoHolder>
{
    List<Alumno> alumnos;


    public AlumnoInfoAdapter(List<Alumno> alumnos)
    {
        this.alumnos = alumnos;
    }

    @NonNull
    @Override
    public AlumnosInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno_info,parent,false);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        return new AlumnosInfoHolder(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoInfoAdapter.AlumnosInfoHolder holder, int position) {

        holder.tvNombreAlumno.setText(alumnos.get(position).getNombre() + " " + alumnos.get(position).getApellido());
       /* holder.tvMinisterio.setText(alumnos.get(position).getMinisterio());
        holder.tvLider.setText(alumnos.get(position).getLider());
        holder.tvTelefono.setText(alumnos.get(position).getTelefono());
        holder.tvDireccion.setText(alumnos.get(position).getDireccion());*/

    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public class AlumnosInfoHolder extends RecyclerView.ViewHolder {

        TextView tvNombreAlumno, tvMinisterio, tvLider, tvTelefono, tvDireccion;


        public AlumnosInfoHolder(View itemView) {
            super(itemView);
            tvNombreAlumno= (TextView) itemView.findViewById(R.id.tvNombreApellido);
            tvMinisterio= (TextView) itemView.findViewById(R.id.tvMinisterio);
            tvLider= (TextView) itemView.findViewById(R.id.tvLider);
            tvTelefono= (TextView) itemView.findViewById(R.id.tvTelefono);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
        }
    }
}
