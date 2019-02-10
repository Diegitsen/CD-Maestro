package com.example.cdmaestro;

import com.example.cdmaestro.Asistencia.AsistenciaFragment;
import com.example.cdmaestro.Notas.NotasFragment;
import com.example.cdmaestro.Reporte.ReporteFragment;

public interface IFragments extends AsistenciaFragment.OnFragmentInteractionListener,
        NotasFragment.OnFragmentInteractionListener,
        ReporteFragment.OnFragmentInteractionListener
{

}
