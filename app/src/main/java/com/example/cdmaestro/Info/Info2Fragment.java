package com.example.cdmaestro.Info;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cdmaestro.Alumno;
import com.example.cdmaestro.AlumnoInfoAdapter;
import com.example.cdmaestro.AlumnoNotaAdapter;
import com.example.cdmaestro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Info2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Info2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Info2Fragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    int idCurso;

    RecyclerView recyclerView;
    ArrayList<Alumno> alumnos;

    ProgressDialog progressDialog;

    RequestQueue request;

    public Info2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Info2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Info2Fragment newInstance(String param1, String param2) {
        Info2Fragment fragment = new Info2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        idCurso = bundle.getInt("ID_CURSO");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_info2, container, false);

        alumnos = new ArrayList<>();

        recyclerView = vista.findViewById(R.id.idRecyclerInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);

        request = Volley.newRequestQueue(getContext());

        checkUserPermissions();

        cargarWebService();

        return vista;
    }

    private void cargarWebService()
    {
        String url = "https://capacitaciondestino.000webhostapp.com/wsJSONFiltrarAlumnosPorCurso.php?idCurso=" + idCurso;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(getContext(), "No se pudo conectar" + error.toString(), Toast.LENGTH_SHORT).show();
        Log.i("ERROR", error.toString());
    }

    @Override
    public void onResponse(JSONObject response)
    {
        Alumno alumno = null;

        JSONArray json = response.optJSONArray("alumno");

        try{
            for(int i = 0; i < json.length(); i++)
            {
                alumno = new Alumno();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                alumno.setNombre(jsonObject.optString("nombre"));
                alumno.setApellido(jsonObject.optString("apellido"));
                alumno.setMinisterio(jsonObject.optInt("idMinisterio"));
                alumno.setLider(jsonObject.optString("lider"));
                alumno.setDireccion(jsonObject.optString("direccion"));
                alumno.setDistrito(jsonObject.optString("distrito"));
                alumno.setTelefono(jsonObject.optInt("telefono"));
                alumno.setAsistencia(jsonObject.optDouble("inasistencias"));

                alumnos.add(alumno);

            }

            AlumnoInfoAdapter adapter = new AlumnoInfoAdapter(alumnos);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(adapter);

        }catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(getContext(), "No se ha podido conectar con el servidor" +
                    response, Toast.LENGTH_SHORT).show();
            progressDialog.hide();
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void checkUserPermissions()
    {
        if(Build.VERSION.SDK_INT>=23)
        {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                // requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                //        REQUEST_CODE_ASK_PERMISSIONS);

                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_ASK_PERMISSIONS);
                return;
            }
        }
        //getSongList();
    }

    static final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case REQUEST_CODE_ASK_PERMISSIONS:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                }
                else
                {
                    //Permission Denied
                    Toast.makeText(getActivity(), "denegado", Toast.LENGTH_SHORT).show();
                }
            }

            default:
            {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }

        }
    }
}
