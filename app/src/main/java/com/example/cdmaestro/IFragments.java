package com.example.cdmaestro;

import com.example.cdmaestro.Asistencia.Asistencia2Fragment;
import com.example.cdmaestro.Asistencia.AsistenciaFragment;
import com.example.cdmaestro.Info.Info2Fragment;
import com.example.cdmaestro.Notas.Notas2Fragment;
import com.example.cdmaestro.Notas.NotasFragment;
import com.example.cdmaestro.Info.InfoFragment;

public interface IFragments extends AsistenciaFragment.OnFragmentInteractionListener,
        NotasFragment.OnFragmentInteractionListener,
        InfoFragment.OnFragmentInteractionListener,
        Asistencia2Fragment.OnFragmentInteractionListener,
        Notas2Fragment.OnFragmentInteractionListener,
        Info2Fragment.OnFragmentInteractionListener
{

}
