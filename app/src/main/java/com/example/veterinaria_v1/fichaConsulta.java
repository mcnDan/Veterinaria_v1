package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import clasesAuxiliares.classFichaConsulta;
import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class fichaConsulta extends AppCompatActivity {
private TextView tvCliente, tvDni, tvNombreM,tvCodigoM;
private ListView lvMostrarFicha;
    private ArrayList<classFichaConsulta> auxConsulta;
private accesoBasedatos basedatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_consulta);
        tvCliente = findViewById(R.id.tv_clienteFC);
        tvDni = findViewById(R.id.tv_dniFC);
        tvNombreM = findViewById(R.id.tv_nomMascFC);
        tvCodigoM = findViewById(R.id.tv_codMascFC);
        lvMostrarFicha = findViewById(R.id.lv_mostrarFicha);


        if (getIntent() != null) {
            //RECIBIENDO DATOS CLIENTE Y MASCOTA
            tvCliente.setText(getIntent().getStringExtra("cliente"));
            tvDni.setText(getIntent().getStringExtra("dni"));
            tvNombreM.setText(getIntent().getStringExtra("nombreM"));
            tvCodigoM.setText(getIntent().getStringExtra("codigoM"));

            String codConsulta = getIntent().getStringExtra("codigoConsulta");
            auxConsulta = new ArrayList<>();
            basedatos = accesoBasedatos.getInstance(getApplicationContext());
            try {
                basedatos.open();
                auxConsulta = basedatos.mostrarConsultasCHCompleto(codConsulta);
                ArrayAdapter<classFichaConsulta> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,auxConsulta);
                lvMostrarFicha.setAdapter(adapter);
                basedatos.close();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Error en el acceso a base de datos",Toast.LENGTH_SHORT).show();
            }

        }
    }
}