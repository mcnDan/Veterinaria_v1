package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import clasesAuxiliares.classBusquedaApellidos;
import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class buscarInicio extends AppCompatActivity {
    private Intent i;
    private EditText apellido;
    private ListView listaClientes;
    private accesoBasedatos database;
    private ArrayList<classBusquedaApellidos> aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_inicio);
        listaClientes = findViewById(R.id.lv_listaClientes);
        apellido = findViewById(R.id.et_buscarApellido);
        try {
            aux = new ArrayList<>();
            database = accesoBasedatos.getInstance(getApplicationContext());
            database.open();
            aux = database.buscarPorApellido("");
            ArrayAdapter<classBusquedaApellidos> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, aux);
            listaClientes.setAdapter(adapter);
            database.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }

        i = new Intent(this,MainActivity.class);
        listaClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dniString = String.valueOf(aux.get(position).getDni());
                i.putExtra("dni",dniString);
                startActivity(i);
            }
        });
        apellido.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                try {
                    aux = new ArrayList<>();
                    database = accesoBasedatos.getInstance(getApplicationContext());
                    database.open();
                    String ape = apellido.getText().toString();
                    aux = database.buscarPorApellido(ape);
                    ArrayAdapter<classBusquedaApellidos> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, aux);
                    listaClientes.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    database.close();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }

    public void buscar(View view) {
        try {
            aux = new ArrayList<>();
            database = accesoBasedatos.getInstance(getApplicationContext());
            database.open();
            String ape = apellido.getText().toString();
            aux = database.buscarPorApellido(ape);
            ArrayAdapter<classBusquedaApellidos> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, aux);
            listaClientes.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            database.close();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    public void goInicio(View view) {
        i= new Intent(this,MainActivity.class);
        startActivity(i);
    }
    public void goListaMascotas(View view){
        i= new Intent(this,listaMascotas.class);
        startActivity(i);
    }
}