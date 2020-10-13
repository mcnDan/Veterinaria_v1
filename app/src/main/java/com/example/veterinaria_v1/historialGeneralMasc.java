package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class historialGeneralMasc extends AppCompatActivity {
private Intent i;
    private TextView cliente,dni,nombreM,codigoM,tvConsulta,tvTratamiento,tvVacuna,tvServiciolc;
accesoBasedatos basedatos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_general_masc);
        cliente = findViewById(R.id.tv_nombreCliente_RM2);
        dni = findViewById(R.id.tv_dniHgm);
        nombreM = findViewById(R.id.tv_nombreMasc);
        codigoM = findViewById(R.id.tv_codigoMasc);
        tvConsulta = findViewById(R.id.tv_consultasHgm);
        tvTratamiento= findViewById(R.id.tv_tratamientoHgm);
        tvVacuna= findViewById(R.id.tv_vacunasHgm);
        tvServiciolc= findViewById(R.id.tv_cortesHgm);
        //recibiendo datos cliente y mascota de listaMascotas.class
        try {
            if(getIntent()!=null){
                cliente.setText(getIntent().getStringExtra("cliente"));
                dni.setText(getIntent().getStringExtra("dni"));
                nombreM.setText(getIntent().getStringExtra("nombreM"));
                codigoM.setText(getIntent().getStringExtra("codigoM"));
                //contando numero de Consul..,trata..,etc por codigo mascota
                basedatos = accesoBasedatos.getInstance(getApplicationContext());
                basedatos.open();
                String numConsultas = basedatos.contarConsultas(codigoM.getText().toString());
                String numTratamiento = basedatos.contarTratamiento((codigoM.getText().toString()));
                String numVacunas = basedatos.contarVacunas(codigoM.getText().toString());
                String numServicios = basedatos.contarLavado(codigoM.getText().toString());
                tvConsulta.setText(numConsultas);
                tvTratamiento.setText(numTratamiento);
                tvVacuna.setText(numVacunas);
                tvServiciolc.setText(numServicios);
                basedatos.close();

            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    public void goInicio(View view) {
        i= new Intent(this,MainActivity.class);
        i.putExtra("dni",dni.getText().toString());
        startActivity(i);
    }
    public void goListaMascotas(View view){
        i= new Intent(this,listaMascotas.class);
        i.putExtra("cliente",cliente.getText().toString());
        i.putExtra("dni", dni.getText().toString());
        startActivity(i);
    }
    public void goFichaTecnica(View view){
        i = new Intent(this,fichaTecnica1.class);
        i.putExtra("cliente",cliente.getText().toString());
        i.putExtra("dni", dni.getText().toString());
        i.putExtra("cliente",cliente.getText().toString());
        i.putExtra("nombreM", nombreM.getText().toString());
        i.putExtra("codigoM", codigoM.getText().toString());
        startActivity(i);
    }
}