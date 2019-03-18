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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
        holder.tvMinisterio.setText(getMinisterio(alumnos.get(position).getMinisterio()));
        holder.tvLider.setText(alumnos.get(position).getLider());
        holder.tvTelefono.setText(alumnos.get(position).getTelefono() + "");
        holder.tvDireccion.setText(alumnos.get(position).getDireccion());
        holder.tvAsistencia.setText(doubleToFormatStringNoDecimals(Math.round(alumnos.get(position).getAsistencia() / 2)) + "");
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

        TextView tvNombreAlumno, tvMinisterio, tvLider, tvTelefono, tvDireccion, tvAsistencia;
        ImageButton ibPhone;

        public AlumnosInfoHolder(View itemView) {
            super(itemView);
            tvNombreAlumno= (TextView) itemView.findViewById(R.id.tvNombreApellido);
            tvMinisterio= (TextView) itemView.findViewById(R.id.tvMinisterio);
            tvLider= (TextView) itemView.findViewById(R.id.tvLider);
            tvTelefono= (TextView) itemView.findViewById(R.id.tvTelefono);
            tvDireccion = (TextView) itemView.findViewById(R.id.tvDireccion);
            tvAsistencia = (TextView) itemView.findViewById(R.id.tvAsistencias);
            ibPhone = itemView.findViewById(R.id.ibPhone);
        }
    }

    public static String doubleToFormatStringNoDecimals(double val){

        DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
        simbolos.setDecimalSeparator('.');
        simbolos.setGroupingSeparator(',');

        DecimalFormat formateador = new DecimalFormat("###,##0", simbolos);
        return formateador.format(val);
    }

    public String getMinisterio(int id)
    {
        String ministerio = null;
        switch (id)
        {
            case 10:
                ministerio = "Campos";
                break;
            case 11:
                ministerio = "Portillo";
                break;
            case 12:
                ministerio = "Mendoza";
                break;
            case 13:
                ministerio = "Hoyos";
                break;
            case 14:
                ministerio = "Sotero";
                break;
            case 15:
                ministerio = "Zárate";
                break;
            case 16:
                ministerio = "Velasquez";
                break;
            case 17:
                ministerio = "Pin";
                break;
            case 18:
                ministerio = "Ramirez";
                break;
            case 19:
                ministerio = "Vilcarromero";
                break;
            case 20:
                ministerio = "Eguez";
                break;
            case 21:
                ministerio = "Cisneros";
                break;
            case 22:
                ministerio = "Tello";
                break;
            case 23:
                ministerio = "Alfaro";
                break;
            case 24:
                ministerio = "Clavijo";
                break;
            case 25:
                ministerio = "Flores";
                break;
            case 26:
                ministerio = "Otros";
                break;
            case 27:
                ministerio = "Anita Hoyos";
                break;
            case 28:
                ministerio = "Marisela Choque";
                break;
            case 29:
                ministerio = "Rocio Vivanco";
                break;
            case 30:
                ministerio = "Cuadros";
                break;
            case 31:
                ministerio = "Imer Centeno";
                break;
            case 32:
                ministerio = "Gary Alfaro";
                break;
            case 33:
                ministerio = "Beto Chambi";
                break;
            case 34:
                ministerio = "De Paz";
                break;
            case 35:
                ministerio = "Hans Rebaza";
                break;
            case 36:
                ministerio = "Wendy Llontop";
                break;
            case 37:
                ministerio = "Equipo 2";
                break;

        }

        return ministerio;
    }

}
