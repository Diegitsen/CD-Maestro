package com.example.cdmaestro;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.cdmaestro.Asistencia.AsistenciaFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements Response.Listener<JSONObject>,
        Response.ErrorListener{

    //layout
    private EditText etUsuario, etContrasenia;
    Button bLogin;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    JsonObjectRequest jsonObjectRequest;

    int idProf = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsuario = findViewById(R.id.etUsuario);
        etContrasenia = findViewById(R.id.etContrasenia);
        bLogin = findViewById(R.id.bLogin);

        requestQueue = Volley.newRequestQueue(this);
    }


    public void cargarWebService(View v)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        String url = "http://192.168.0.14/CapacitacionDestino/wsJSONLoginProfesor.php?username=" + etUsuario.getText().toString() +
                "&contrasenia=" + etContrasenia.getText().toString();

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        //jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response)
    {

        progressDialog.hide();
        //Toast.makeText(getContext(),"Mensaje: " + response, Toast.LENGTH_SHORT).show();

        Profesor profesor = new Profesor();

        JSONArray json = response.optJSONArray("Profesor");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            profesor.setIdProfesor(jsonObject.optInt("idProfesor"));
            profesor.setNombre(jsonObject.optString("nombre"));
            profesor.setApellido(jsonObject.optString("apellido"));
            profesor.setMinisterio(jsonObject.optString("ministerio"));
            profesor.setUsername(jsonObject.optString("username"));
            profesor.setContrasenia(jsonObject.optString("contrasenia"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(!(profesor.getUsername().isEmpty()) && !(profesor.getContrasenia().isEmpty()))
        {
            Toast.makeText(this, "Se logeo ", Toast.LENGTH_SHORT).show();
            goToActivity(profesor.getIdProfesor());
        }
        else
            Toast.makeText(this, "no Se logeo ", Toast.LENGTH_SHORT).show();

    }

    public int getIdProf()
    {
        return idProf;
    }

    public void goToActivity(int id)
    {
        Intent i = new Intent(this, MainActivity.class);

        Bundle b = new Bundle();
        b.putInt("ID_PROFESOR", id);
        i.putExtras(b);


        /*
        Bundle bundle = new Bundle();
        bundle.putInt("ID_PROFESOR", id);
        Fragment asistenciaFragment = new AsistenciaFragment();
        asistenciaFragment.setArguments(bundle);*/
        startActivity(i);
    }
}
