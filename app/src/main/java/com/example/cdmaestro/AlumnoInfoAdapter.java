package com.example.cdmaestro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AlumnoInfoAdapter extends RecyclerView.Adapter<AlumnoInfoAdapter.AlumnosInfoHolder> {
    List<Alumno> alumnos;

    private Context context;


    public AlumnoInfoAdapter(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }


    @NonNull
    @Override
    public AlumnosInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno_info, parent, false);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        vista.setLayoutParams(layoutParams);
        context = parent.getContext();
        return new AlumnosInfoHolder(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull final AlumnosInfoHolder holder, final int position) {

        holder.tvNombreAlumno.setText(alumnos.get(position).getNombre() + " " + alumnos.get(position).getApellido());
        holder.tvMinisterio.setText(alumnos.get(position).getMinisterio() + "");
        holder.tvLider.setText(alumnos.get(position).getLider());
        holder.tvTelefono.setText(alumnos.get(position).getTelefono() + "");
        holder.tvDireccion.setText(alumnos.get(position).getDireccion());
        holder.ibPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + alumnos.get(position).getTelefono()));

                //para comprobar si tiene permiso
                if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                v.getContext().startActivity(callIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return alumnos.size();
    }

    public class AlumnosInfoHolder extends RecyclerView.ViewHolder {

        TextView tvNombreAlumno, tvMinisterio, tvLider, tvTelefono, tvDireccion;
        ImageButton ibPhone;

        public AlumnosInfoHolder(View itemView) {
            super(itemView);
            tvNombreAlumno= (TextView) itemView.findViewById(R.id.tvNombreApellido);
            tvMinisterio= (TextView) itemView.findViewById(R.id.tvMinisterio);
            tvLider= (TextView) itemView.findViewById(R.id.tvLider);
            tvTelefono= (TextView) itemView.findViewById(R.id.tvTelefono);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
            ibPhone = itemView.findViewById(R.id.ibPhone);
        }
    }

}
