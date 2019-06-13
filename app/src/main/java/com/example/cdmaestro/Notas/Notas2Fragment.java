package com.example.cdmaestro.Notas;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cdmaestro.Alumno;
import com.example.cdmaestro.AlumnoAdapter;
import com.example.cdmaestro.AlumnoNotaAdapter;
import com.example.cdmaestro.Alumno_Curso;
import com.example.cdmaestro.EditModel;
import com.example.cdmaestro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Notas2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Notas2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notas2Fragment extends Fragment implements Response.Listener<JSONObject>,
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
    int nCurso=0;
    int nAlumnos = 0;
    int idNota = 0;
    private Button bEnviarDatos;
    boolean passNote = true;

    RecyclerView recyclerView;
    ArrayList<Alumno> alumnos;

    ProgressDialog progressDialog;

    RequestQueue request;

    private Spinner spinner;

    public ArrayList<EditModel> editModelArrayList;

    private int nService = 0;
    private List<Integer> listOfIds = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();


    public Notas2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notas2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Notas2Fragment newInstance(String param1, String param2) {
        Notas2Fragment fragment = new Notas2Fragment();
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
        View vista = inflater.inflate(R.layout.fragment_notas2, container, false);

        alumnos = new ArrayList<>();



        recyclerView = vista.findViewById(R.id.idRecyclerNotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        bEnviarDatos = vista.findViewById(R.id.bSaveData);

        spinner = vista.findViewById(R.id.sNota);
        setUpSpinnerNotas(spinner);

        editModelArrayList = populateList();

        bEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for(int j = 0; j < alumnos.size(); j++)
                {
                    if(editModelArrayList.get(j).getEditTextValue()==null)
                    {
                        passNote = false;
                    }
                }

                if(passNote==true)
                {
                    for(int i = 0; i < alumnos.size(); i++)
                    {
                        //if(allCb.get(i).isChecked())
                        // {
                        //cargarWebService2(allCb.get(i).getId());
                        // cargarWebService4(allCb.get(i).getId());
                        cargarWebService2(ids.get(i));
                        nAlumnos++;
                        // }
                    }
                }
                else
                {
                    Toast.makeText(getActivity(), "Debe de insertar la nota a todos los alumnos", Toast.LENGTH_LONG).show();
                    passNote = true;
                }

            }
        });

        request = Volley.newRequestQueue(getContext());

        cargarWebService();



        //b enviar datos

        return vista;

    }

    private void cargarWebService()
    {
        nService = 1;
        String url = "https://capacitaciondestino.000webhostapp.com/wsJSONFiltrarAlumnosPorCurso.php?idCurso=" + idCurso;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    private void cargarWebService2(int idAlumno)
    {
        nService = 2;
        String url = "https://capacitaciondestino.000webhostapp.com/wsJSONGetIdAlumnoCurso.php?idAlumno=" + idAlumno + "&idCurso=" + idCurso;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);

    }

    private void cargarWebService3(List<Integer> list, int idNotax)
    {
        nService = 3;

        for(int i = 0; i < list.size(); i++)
        {
            String url = "https://capacitaciondestino.000webhostapp.com/wsJSONPutNota.php?nota=" + editModelArrayList.get(i).getEditTextValue() + "&idAlumnoCurso=" + list.get(i) + "&idNota=" + idNotax;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            request.add(jsonObjectRequest);
        }

        //on response yeeeei
        Toast.makeText(getContext(), "Se ha registrado las notas correctamente", Toast.LENGTH_SHORT).show();


    }

    private void cargarWebService4(List<Integer> list, int idNota)
    {
        nService = 4;

        for(int i = 0; i < list.size(); i++)
        {
            String url = "https://capacitaciondestino.000webhostapp.com/wsJSONGetNota.php?idAlumnoCurso=" + list.get(i) + "&idNota" + idNota;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            request.add(jsonObjectRequest);
        }

        //on response yeeeei
       // Toast.makeText(getContext(), "Se ha registrado las notas correctamente", Toast.LENGTH_SHORT).show();

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

        if(nService==4)
        {
            //  Toast.makeText(getContext(), "Se ha marcado asistencia correctamente", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(getContext(), "No se pudo conectar" + error.toString(), Toast.LENGTH_SHORT).show();
            Log.i("ERROR", error.toString());
        }
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
                    alumno.setApellido(jsonObject.optString("apellido"));
                    alumno.setIdAlumno(jsonObject.optInt("idAlumno"));

                    ids.add(alumno.getIdAlumno());

                    alumnos.add(alumno);

                }

                AlumnoNotaAdapter adapter = new AlumnoNotaAdapter(alumnos, editModelArrayList);
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
            //cargarWebService3(alumno_curso.getIdAlumnoCurso(), idCurso);
            listOfIds.add(alumno_curso.getIdAlumnoCurso());

            if(listOfIds.size()==nAlumnos)
            {
                cargarWebService3(listOfIds, idNota);
            }

        }
        else if(nService == 3)
        {
            // Toast.makeText(getContext(), "Se ha marcado asistencia correctamente", Toast.LENGTH_SHORT).show();

        }
        else if(nService==4)
        {
            Toast.makeText(getContext(), "Se ha registrado las notas correctamente", Toast.LENGTH_SHORT).show();

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

    public void setUpSpinnerNotas(Spinner spinnerNotas)
    {
        spinnerNotas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                String item = parent.getItemAtPosition(position).toString();

                switch (item)
                {
                    case "Cuest. 1":
                        idNota = 1;
                        break;
                    case "Cuest. 2":
                        idNota = 2;
                        break;
                    case "Cuest. 3":
                        idNota = 3;
                        break;
                    case "Cuest. 4":
                        idNota = 4;
                        break;
                    case "Cuest. 5":
                        idNota = 5;
                        break;
                    case "Cuest. 6":
                        idNota = 6;
                        break;
                    case "Cuest. 7":
                        idNota = 7;
                        break;
                    case "Cuest. 8":
                        idNota = 8;
                        break;
                    case "Cuest. 9":
                        idNota = 9;
                        break;
                    case "Cuest. 10":
                        idNota = 10;
                        break;
                    case "Trabajo":
                        idNota = 11;
                        break;
                    case "Parcial":
                        idNota = 12;
                        break;
                    case "Total":
                        idNota = 13;
                        break;
                    case "Asistencia":
                        idNota = 14;
                        break;
                    case "Servicio":
                        idNota = 15;
                        break;
                    case "Intercesion":
                        idNota = 16;
                        break;
                    case "Devocional":
                        idNota = 17;
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        List<String> nota  = new ArrayList<String>();
        nota.add("Cuest. 1");
        nota.add("Cuest. 2");
        nota.add("Cuest. 3");
        nota.add("Cuest. 4");
        nota.add("Cuest. 5");
        nota.add("Cuest. 6");
        nota.add("Cuest. 7");
        nota.add("Cuest. 8");
        nota.add("Cuest. 9");
        nota.add("Cuest. 10");
        nota.add("Trabajo");
        nota.add("Parcial");
        nota.add("Final");
        nota.add("Asistencia");
        nota.add("Servicio");
        nota.add("Intercesion");
        nota.add("Devocional");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, nota);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerNotas.setAdapter(dataAdapter);
    }

    private ArrayList<EditModel> populateList(){

        ArrayList<EditModel> list = new ArrayList<>();

        for(int i = 0; i < 40; i++){
            EditModel editModel = new EditModel();
            editModel.setEditTextValue(null);
            list.add(editModel);
        }

        return list;
    }
}
