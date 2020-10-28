package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import clasesAuxiliares.classListaConsultas;
import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class consultasHistorial extends AppCompatActivity {
    accesoBasedatos basedatos;
    private TextView tvDni, tvCliente, tvNombreM, tvCodigoM;
    private RadioButton rbMotivo, rbFecha, rbCodigoC;
    private EditText etBuscar;
    private ListView listaConsultas;
    private Intent i;
    private ArrayList<classListaConsultas> aux;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultas_historial);
        //INICIALIZANDO VARIABLES : DATOS MASCOTA Y CLIENTE
        tvDni = findViewById(R.id.tv_dniCH);
        tvCliente = findViewById(R.id.tv_nomClienteCH);
        tvNombreM = findViewById(R.id.tv_nomMascCH);
        tvCodigoM = findViewById(R.id.tv_codMascCH);
        //INICIALIZANDO RADIOBUTTON - EDITTEXT - LISTVIEW
        rbCodigoC = findViewById(R.id.rb_codCodCH);
        rbFecha = findViewById(R.id.rb_fechCH);
        rbMotivo = findViewById(R.id.rb_motivoCH);
        etBuscar = findViewById(R.id.tv_buscarCH);
        listaConsultas = findViewById(R.id.lv_consultasCH);


        //RECIBIENDO DATOS CLIENTE Y MASCOTA
        tvCliente.setText(getIntent().getStringExtra("cliente"));
        tvDni.setText(getIntent().getStringExtra("dni"));
        tvNombreM.setText(getIntent().getStringExtra("nombreM"));
        tvCodigoM.setText(getIntent().getStringExtra("codigoM"));
        //etBuscar.setEnabled(false);

        llenarListaConsulta(tvCodigoM.getText().toString(), "cod_mascota");

        listaConsultas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ENVIANDO DATOS DE CLIENTE Y MASCOTA
                i = new Intent(getApplicationContext(), fichaConsulta.class);
                i.putExtra("dni", tvDni.getText().toString());
                i.putExtra("cliente", tvCliente.getText().toString());
                i.putExtra("nombreM", tvNombreM.getText().toString());
                i.putExtra("codigoM", tvCodigoM.getText().toString());
                //ENVIANDO DATA CONSULTA
                i.putExtra("codigoConsulta", aux.get(position).codConsulta);
                startActivity(i);

            }
        });
    }

    public void llenarListaConsulta(String codConsulta, String columnTable) {
        basedatos = accesoBasedatos.getInstance(getApplicationContext());
        basedatos.open();
        aux = new ArrayList<>();
        aux = basedatos.mostrarConsultasCH(tvCodigoM.getText().toString());
        ArrayAdapter<classListaConsultas> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aux);
        listaConsultas.setAdapter(adapter);
        basedatos.close();

    }

    public void buscarConsulta(View view) {
        if (rbCodigoC.isChecked()) {            //etBuscar.setEnabled(true);
            basedatos = accesoBasedatos.getInstance(getApplicationContext());
            basedatos.open();
            aux = new ArrayList<>();
            aux = basedatos.mostrarConsultasCHxcodigo(etBuscar.getText().toString());
            ArrayAdapter<classListaConsultas> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aux);
            listaConsultas.setAdapter(adapter);
            basedatos.close();
        }
        //else
        if (rbMotivo.isChecked()) {
            //llenarListaConsulta(etBuscar.getText().toString(),"motivo");
            basedatos = accesoBasedatos.getInstance(getApplicationContext());
            basedatos.open();
            aux = new ArrayList<>();
            aux = basedatos.mostrarConsultasCHxMotivo(tvCodigoM.getText().toString(), etBuscar.getText().toString());
            ArrayAdapter<classListaConsultas> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aux);
            listaConsultas.setAdapter(adapter);
            basedatos.close();
        }

        if (rbFecha.isChecked()) {
            basedatos = accesoBasedatos.getInstance(getApplicationContext());
            basedatos.open();
            aux = new ArrayList<>();
            aux = basedatos.mostrarConsultasCHxfecha(tvCodigoM.getText().toString(), etBuscar.getText().toString());
            ArrayAdapter<classListaConsultas> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, aux);
            listaConsultas.setAdapter(adapter);
            basedatos.close();
        }
        //else
        //Toast.makeText(getApplicationContext(),"No se encontoro el dato", Toast.LENGTH_SHORT).show();
        if (etBuscar.getText().toString().isEmpty()) {
            llenarListaConsulta(tvCodigoM.getText().toString(), "cod_mascota");
            Toast.makeText(getApplicationContext(), "Ingrese dato a buscar", Toast.LENGTH_SHORT).show();
        }

    }
}