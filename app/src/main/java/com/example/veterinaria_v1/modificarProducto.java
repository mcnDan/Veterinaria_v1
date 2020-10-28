package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import clasesAuxiliares.classProductos;
import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class modificarProducto extends AppCompatActivity {
private ImageView imgProducto;
private EditText codProducto, nomProducto,costoProducto,precioProducto,cantidadProducto,descriProducto;
private accesoBasedatos basedatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_producto);
        imgProducto = findViewById(R.id.iv_imagenProdModi);
        codProducto = findViewById(R.id.et_codProducModi);
        nomProducto = findViewById(R.id.et_nomProdModi);
        costoProducto = findViewById(R.id.et_costoProdModi);
        precioProducto = findViewById(R.id.et_precioProdModi);
        cantidadProducto = findViewById(R.id.et_cantidadProdModi);
        descriProducto = findViewById(R.id.et_descriProModi);

    }
    public void buscarProducto(View view){
        basedatos = accesoBasedatos.getInstance(getApplicationContext());
        try {
            basedatos.open();
            classProductos cp = new classProductos();
            cp = basedatos.mostrarProdxCod(codProducto.getText().toString());
            nomProducto.setText(cp.getNomProducto());
            costoProducto.setText(String.valueOf(cp.getCostoProduco()));
            precioProducto.setText(String.valueOf(cp.getPrecioProducto()));
            cantidadProducto.setText(String.valueOf(cp.getStockProducto()));
            descriProducto.setText(cp.getDescripcion());
            //DECODIFICANDO
            String base64String = cp.imgProducto;
            byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imgProducto.setImageBitmap(decodedByte);
            Toast.makeText(this, "Entro", Toast.LENGTH_SHORT).show();
            basedatos.close();
        } catch (Exception e) {
            Toast.makeText(this, "no Entro", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            basedatos.close();
        }
    }
}