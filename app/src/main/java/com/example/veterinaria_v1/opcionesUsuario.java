package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class opcionesUsuario extends AppCompatActivity {
Intent i;
RadioButton rbProducto, rbProveedor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_usuario);
        rbProducto = findViewById(R.id.rb_productoOp);
        rbProveedor = findViewById(R.id.rb_proveedorOp);
        rbProducto.setChecked(true);
    }
    //BOTON MODIFICAR
    public void openModificar_Produc_Provee(View view){
        if(rbProducto.isChecked()) {
            i = new Intent(this, modificarProducto.class);
            startActivity(i);
        }
        else{
            i = new Intent(this, modificarProveedor.class);
            startActivity(i);
        }

    }
    //BOTON REGISTRAR
    public void openRegistrar_Produc_Prove(View view){
        if(rbProducto.isChecked()){
            i = new Intent(this,registrarProducto.class);
            startActivity(i);
        }
        else{
            i = new Intent(this, registrarProveedor.class);
            startActivity(i);
        }
    }
    //BOTON REGISTRAR COMPRA
    public void registrarCompra(View view){
        i = new Intent(this, registrarCompraPP.class);
        startActivity(i);
    }
    //BOTON LISTA PRODCUTOS
    public void mostrarListaProductos(View view){
        i = new Intent(this, listaProducto.class);
        startActivity(i);
    }
}