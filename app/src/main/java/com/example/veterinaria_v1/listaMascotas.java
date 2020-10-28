package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import clasesAuxiliares.classMascota;
import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class listaMascotas extends AppCompatActivity {
    private Intent i;
    private TextView cliente;
    private TextView dni;
    private ListView listaMascotas;
    private accesoBasedatos basedatos;
    private ArrayList<classMascota> auxMascotaAl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_mascotas);
        cliente = findViewById(R.id.tv_nombreCliente);
        dni = findViewById(R.id.tv_dniCliente);
        //Mostrando datos cliente en TextView recibidos de Main...class
        listaMascotas = findViewById(R.id.lv_listaMascotas);

            cliente.setText(getIntent().getStringExtra("cliente"));
            dni.setText(getIntent().getStringExtra("dni"));

        //mostrando datos mascota en listview por DNI
        auxMascotaAl = new ArrayList();
        int dniInt = Integer.parseInt(dni.getText().toString());
        try {
            basedatos = accesoBasedatos.getInstance(getApplicationContext());
            basedatos.open();
            auxMascotaAl = basedatos.getMascota(dniInt);
            ArrayAdapter<classMascota> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, auxMascotaAl);
            listaMascotas.setAdapter(adapter);
            //pasando a historialGeneral.class a traves de item de listview y enviando datos cliente y mascota

            listaMascotas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    i = new Intent(getApplicationContext(), historialGeneralMasc.class);
                    String codigoM = auxMascotaAl.get(position).getCodigo();
                    String nombreM= auxMascotaAl.get(position).getNombre();
                    i.putExtra("cliente",cliente.getText().toString());
                    i.putExtra("dni",dni.getText().toString());
                    i.putExtra("nombreM",nombreM);
                    i.putExtra("codigoM",codigoM);
                    startActivity(i);
                }
            });
            basedatos.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    public void registrarNuevaMascota(View view){
        i = new Intent(this,registrarMascota.class);
        i.putExtra("dni",dni.getText().toString());
        i.putExtra("cliente",cliente.getText().toString());
        startActivity(i);
    }

    public void goInicio(View view) {
        i = new Intent(this, MainActivity.class);
        i.putExtra("dni",dni.getText().toString());
        startActivity(i);
    }
}
