package com.example.cdmaestro.Asistencia;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cdmaestro.Alumno;
import com.example.cdmaestro.AlumnoAdapter;
import com.example.cdmaestro.Alumno_Curso;
import com.example.cdmaestro.Curso;
import com.example.cdmaestro.CursoAdapter;
import com.example.cdmaestro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Asistencia2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Asistencia2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Asistencia2Fragment extends Fragment implements Response.Listener<JSONObject>,
        Response.ErrorListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    int idCurso;
    private LinearLayout linearLayout_checkboxes;
    private final ArrayList<CheckBox> allCb = new ArrayList<>();
    private Button bEnviarDatos;

    private int nService = 0;

    RecyclerView recyclerView;
    ArrayList<Alumno> alumnos;

    ProgressDialog progressDialog;

    RequestQueue request;

    public Asistencia2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Asistencia2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Asistencia2Fragment newInstance(String param1, String param2) {
        Asistencia2Fragment fragment = new Asistencia2Fragment();
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
        View vista = inflater.inflate(R.layout.fragment_asistencia2, container, false);

        alumnos = new ArrayList<>();
        linearLayout_checkboxes = (LinearLayout) vista.findViewById(R.id.linearLayout_checkboxes);
        bEnviarDatos = vista.findViewById(R.id.bSaveData);

        /*recyclerView = vista.findViewById(R.id.idRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);*/

        bEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < allCb.size(); i++)
                {
                    if(allCb.get(i).isChecked())
                    {
                        cargarWebService2(allCb.get(i).getId());
                    }
                }


            }
        });

        request = Volley.newRequestQueue(getContext());


        cargarWebService();

        return vista;
    }

    private void cargarWebService()
    {
        nService = 1;
        String url = "http://192.168.0.14/CapacitacionDestino/wsJSONFiltrarAlumnosPorCurso.php?idCurso=" + idCurso;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    private void cargarWebService2(int idAlumno)
    {
        nService = 2;

        String url = "http://192.168.0.14/CapacitacionDestino/wsJSONGetIdAlumnoCurso.php?idAlumno=" + idAlumno + "&idCurso=" + idCurso;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    private void cargarWebService3(int idAlumnoCurso, int idClase)
    {
        nService = 3;

        String url = "http://192.168.0.14/CapacitacionDestino/wsJSONPonerAsistencia.php?asistio=" + 1 + "&idAlumnoCurso=" + idAlumnoCurso + "&idClase=" + idClase;

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
        if(nService==1)
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
                    alumno.setIdAlumno(jsonObject.optInt("idAlumno"));

                    CheckBox cb = new CheckBox(getActivity());
                    cb.setText(alumno.getNombre());
                    cb.setId(alumno.getIdAlumno());
                    linearLayout_checkboxes.addView(cb);
                    allCb.add(cb);

                    alumnos.add(alumno);

                }

            /*AlumnoAdapter adapter = new AlumnoAdapter(alumnos);
            recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

            recyclerView.setAdapter(adapter);*/

            }catch (JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(getContext(), "No se ha podido conectar con el servidor" +
                        response, Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }

        }

            else if(nService == 2)
        {
           // progressDialog.hide();

            Alumno_Curso alumno_curso = new Alumno_Curso();

            JSONArray json = response.optJSONArray("alumno_curso");
            JSONObject jsonObject=null;

            try {
                jsonObject=json.getJSONObject(0);
                alumno_curso.setIdAlumnoCurso(jsonObject.optInt("idAlumnoCurso"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

           // Toast.makeText(getActivity(), "" + alumno_curso.getIdAlumnoCurso(), Toast.LENGTH_SHORT).show();
            cargarWebService3(alumno_curso.getIdAlumnoCurso(), idCurso);
        }
        else if(nService == 3)
        {
            Toast.makeText(getContext(), "Se ha marcado asistencia correctamente", Toast.LENGTH_SHORT).show();

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

}
