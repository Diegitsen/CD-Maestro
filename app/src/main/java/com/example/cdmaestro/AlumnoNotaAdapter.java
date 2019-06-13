package com.example.cdmaestro;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AlumnoNotaAdapter extends RecyclerView.Adapter<AlumnoNotaAdapter.AlumnosNotasHolder>
{

    List<Alumno> alumnos;
    public static ArrayList<EditModel> editModelArrayList;

    Context context;

    public AlumnoNotaAdapter(List<Alumno> alumnos, ArrayList<EditModel> editModelArrayList)
    {
        this.alumnos = alumnos;
        this.editModelArrayList = editModelArrayList;
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

        Alumno alumno = alumnos.get(position);

        holder.tvNombreAlumno.setText(alumno.getNombre() + " " + alumno.getApellido());
       // if(editModelArrayList.get(position).getEditTextValue() != null)
        holder.etNota.setText(editModelArrayList.get(position).getEditTextValue());
        //holder.etNota.getText();
//        alumno.setNota(Integer.parseInt(holder.etNota.getText().toString()));
  //      holder.etNota.setText(alumno.getNota());
       // holder.tvNombreAlumno.setText(alumnos.get(position).getNombre().toString() + " " + alumnos.get(position).getApellido());
    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public class AlumnosNotasHolder extends RecyclerView.ViewHolder {

        TextView tvNombreAlumno;
        EditText etNota;


        public AlumnosNotasHolder(View itemView) {
            super(itemView);

            tvNombreAlumno= (TextView) itemView.findViewById(R.id.tvNombreAlumno);
            etNota = (EditText) itemView.findViewById(R.id.etNota);
            etNota.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    editModelArrayList.get(getAdapterPosition()).setEditTextValue(etNota.getText().toString());

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

}
