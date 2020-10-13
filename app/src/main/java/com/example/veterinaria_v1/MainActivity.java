package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import veterinaria_v1.basedatosConeccion.accesoBasedatos;


public class MainActivity extends AppCompatActivity {
    Intent i;
    private EditText dni;
    private accesoBasedatos basedatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.dni = (EditText) findViewById(R.id.et_dniInicio);
        if (getIntent()!=null)
            dni.setText(getIntent().getStringExtra("dni"));

    }

    public void buscarCliente(View view) {
        i = new Intent(this, buscarInicio.class);
        startActivity(i);
    }

    public void ingresar(View view) {
        int dniInt=0;
        String dniString = dni.getText().toString();

        if (dniString.isEmpty())
            dni.setError("Ingrese DNI");
        else {
            try {
                dniInt= Integer.parseInt(dniString);
                basedatos = accesoBasedatos.getInstance(getApplicationContext());
                basedatos.open();
                String auxCliente = basedatos.getCliente(dniInt);

                if( auxCliente.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "DNI no registrado!", Toast.LENGTH_SHORT).show();
                    dni.setText("");
                    basedatos.close();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Entrando a la informacion del Cliente", Toast.LENGTH_SHORT).show();
                    i = new Intent(this, listaMascotas.class);
                    i.putExtra("dni",dniString);
                    i.putExtra("cliente",auxCliente);
                    startActivity(i);
                    basedatos.close();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                dni.setText("");
            }
        }


    }

    public void registrarNCliente(View view) {
        i = new Intent(this, registrarCliente.class);
        startActivity(i);
    }

    public void salir(View view) {
        i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}