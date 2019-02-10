package com.example.cdmaestro;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.cdmaestro.Asistencia.AsistenciaFragment;
import com.example.cdmaestro.Notas.NotasFragment;
import com.example.cdmaestro.Reporte.ReporteFragment;

public class MainActivity extends AppCompatActivity implements IFragments {

    private TextView mTextMessage;

    int idProf = 2;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        Fragment frag = null;
        boolean fragmentSeleccionado = false;

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_asistencia:
                    frag = new AsistenciaFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID_PROFESOR", idProf);
                    frag.setArguments(bundle);
                    fragmentSeleccionado = true;
                    break;

                case R.id.navigation_notas:
                    frag = new NotasFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("ID_PROFESOR", idProf);
                    frag.setArguments(bundle2);
                    fragmentSeleccionado = true;
                    break;

                case R.id.navigation_reporte:
                    frag = new ReporteFragment();
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("ID_PROFESOR", idProf);
                    frag.setArguments(bundle3);
                    fragmentSeleccionado = true;
                    break;
            }

            if(fragmentSeleccionado==true)
            {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle b = getIntent().getExtras();
        idProf =  b.getInt("ID_PROFESOR",-1);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //Iniciar el fragment
        AsistenciaFragment asistenciaFragment = new AsistenciaFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("ID_PROFESOR", idProf);
        asistenciaFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.container, asistenciaFragment).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
