package com.example.cdmaestro.Ver;

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
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cdmaestro.Alumno;
import com.example.cdmaestro.AlumnoNota2Adapter;
import com.example.cdmaestro.AlumnoNotaAdapter;
import com.example.cdmaestro.Alumno_Curso;
import com.example.cdmaestro.Alumno_Nota;
import com.example.cdmaestro.EditModel;
import com.example.cdmaestro.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.cdmaestro.AlumnoNotaAdapter.editModelArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Ver2Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Ver2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Ver2Fragment extends Fragment implements Response.Listener<JSONObject>,
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

    public ArrayList<EditModel> editModelArrayList;


    RecyclerView recyclerView;
    ArrayList<Alumno> alumnos;
    ArrayList<Alumno_Nota> alumnos_notas = new ArrayList<>();


    ProgressDialog progressDialog;

    RequestQueue request;

    private Spinner spinner;


    private int nService = 0;
    private List<Integer> listOfIds = new ArrayList<>();
    private List<Integer> ids = new ArrayList<>();

    public Ver2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Ver2Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Ver2Fragment newInstance(String param1, String param2) {
        Ver2Fragment fragment = new Ver2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            idCurso = bundle.getInt("ID_CURSO");
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_ver2, container, false);

        alumnos = new ArrayList<>();

        recyclerView = vista.findViewById(R.id.idRecyclerNotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setHasFixedSize(true);
        bEnviarDatos = vista.findViewById(R.id.bSaveData);


        spinner = vista.findViewById(R.id.sNota);
        setUpSpinnerNotas(spinner);

        editModelArrayList = populateList();



        /*bEnviarDatos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/

        request = Volley.newRequestQueue(getContext());

        cargarWebService();

        return vista;
    }

    private void cargar()
    {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Cargando...");
        progressDialog.show();
        nAlumnos = 0;
        listOfIds.clear();
        //ids.clear();
        alumnos_notas.clear();

        for(int i = 0; i < alumnos.size(); i++)
        {
            cargarWebService2(ids.get(i));
            nAlumnos++;
        }
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
            String url = "https://capacitaciondestino.000webhostapp.com/wsJSONGetNota.php?idAlumnoCurso=" + list.get(i) + "&idNota=" + idNotax;

            Log.e("url", url);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
            request.add(jsonObjectRequest);
        }

        //on response yeeeei
        Toast.makeText(getContext(), "Datos cargados de forma exitosa", Toast.LENGTH_SHORT).show();
        Log.e("n alumnos: ", alumnos.size() + "");
        progressDialog.hide();


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
    public void onResponse(JSONObject response) {


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


            Alumno_Curso alumno_curso = new Alumno_Curso();

            JSONArray json = response.optJSONArray("alumno_curso");
            JSONObject jsonObject=null;

            try {
                jsonObject=json.getJSONObject(0);
                alumno_curso.setIdAlumnoCurso(jsonObject.optInt("idAlumnoCurso"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            listOfIds.add(alumno_curso.getIdAlumnoCurso());

            if(listOfIds.size()==nAlumnos)
            {
                cargarWebService3(listOfIds, idNota);

                Log.e("N. de ids: ", listOfIds.size()+"");
            }

        }
        else if(nService == 3)
        {
            Alumno_Nota alumno_nota = null;

            JSONArray json = response.optJSONArray("alumno_curso");


            try{
                for(int i = 0; i < json.length(); i++)
                {
                    alumno_nota = new Alumno_Nota();
                    JSONObject jsonObject = null;
                    jsonObject = json.getJSONObject(i);

                    alumno_nota.setNombre(jsonObject.optString("nombre"));
                    alumno_nota.setApellido(jsonObject.optString("apellido"));
                    alumno_nota.setNota(jsonObject.optInt("nota"));


                    ids.add(alumno_nota.getIdAlumno());

                    alumnos_notas.add(alumno_nota);

                }


                AlumnoNota2Adapter adapter = new AlumnoNota2Adapter(alumnos_notas);
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
               // alumnos_notas.clear();

            }catch (JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(getContext(), "No se ha podido conectar con el servidor" +
                        response, Toast.LENGTH_SHORT).show();
                progressDialog.hide();
            }
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
                    case "Seleccione nota":

                        break;
                    case "Cuest. 1":
                        idNota = 1;
                        cargar();
                        break;
                    case "Cuest. 2":
                        idNota = 2;
                        cargar();
                        break;
                    case "Cuest. 3":
                        idNota = 3;
                        cargar();
                        break;
                    case "Cuest. 4":
                        idNota = 4;
                        cargar();
                        break;
                    case "Cuest. 5":
                        idNota = 5;
                        cargar();
                        break;
                    case "Cuest. 6":
                        idNota = 6;
                        cargar();
                        break;
                    case "Cuest. 7":
                        idNota = 7;
                        cargar();
                        break;
                    case "Cuest. 8":
                        idNota = 8;
                        cargar();
                        break;
                    case "Cuest. 9":
                        idNota = 9;
                        cargar();
                        break;
                    case "Cuest. 10":
                        idNota = 10;
                        cargar();
                        break;
                    case "Trabajo":
                        idNota = 11;
                        cargar();
                        break;
                    case "Parcial":
                        idNota = 12;
                        cargar();
                        break;
                    case "Total":
                        idNota = 13;
                        cargar();
                        break;
                    case "Asistencia":
                        idNota = 14;
                        cargar();
                        break;
                    case "Servicio":
                        idNota = 15;
                        cargar();
                        break;
                    case "Intercesion":
                        idNota = 16;
                        cargar();
                        break;
                    case "Devocional":
                        idNota = 17;
                        cargar();
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        List<String> nota  = new ArrayList<String>();
        nota.add("Seleccione nota");
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
