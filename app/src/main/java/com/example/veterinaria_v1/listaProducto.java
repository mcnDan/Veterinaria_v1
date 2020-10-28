package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import clasesAuxiliares.classProductos;
import clasesAuxiliares.listaProdAdapter;
import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class listaProducto extends AppCompatActivity {
private ListView lvListaProductos;
private accesoBasedatos basedatos;
private Intent i ;
private listaProdAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);
        lvListaProductos = findViewById(R.id.lv_lista_productosLP);
        //adapter.clear();
        ArrayList<classProductos> aux = new ArrayList<>();
        try {
            basedatos = accesoBasedatos.getInstance(getApplicationContext());
            basedatos.open();
            aux = basedatos.mostrarProductos();
            basedatos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter = new listaProdAdapter(this,R.layout.lista_productos_vet,aux);
        adapter.notifyDataSetChanged();
        lvListaProductos.setAdapter(adapter);

    }

}