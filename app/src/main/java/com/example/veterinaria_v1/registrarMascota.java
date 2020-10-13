package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import clasesAuxiliares.classEspecieMascota;
import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class registrarMascota extends AppCompatActivity {
    private TextView dni, cliente;
    private String codigoM, codigoE, nombreE,dniString,nombreString,razaString,sexoString,periodoString;
    private double edadDouble;
    private EditText nombreM, edadM;
    private Spinner especie, raza, sexo, periodo;
    private accesoBasedatos basedatos;
    private classEspecieMascota especieMascota;
    private ArrayList<classEspecieMascota> especieMascotasAux;
    private ArrayList<String> especieString;
    private ArrayList<String> razaPerros;
    private ArrayList<String> razaGatos;
    private ArrayList<String> razaConejos;
    private ArrayList<String> razaHamster;
    private Intent i;
    //ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_mascota);

        dni = findViewById(R.id.tv_dni_RM);
        nombreM = findViewById(R.id.et_nombreM);
        cliente = findViewById(R.id.tv_nombreCliente_RM);
        especie = findViewById(R.id.sp_especie);
        raza = findViewById(R.id.sp_razas);
        sexo = findViewById(R.id.sp_sexo);
        edadM = findViewById(R.id.et_edad);
        periodo = findViewById(R.id.sp_mesesAnios);
        //creando arrays para spinner (razas de animales)
        //PERROS
        razaPerros = new ArrayList<>();
        razaPerros.add("Husky Siberiano");
        razaPerros.add("Golden Retriever");
        razaPerros.add("Poodle");
        razaPerros.add("Pastor Aleman");
        razaPerros.add("Yorkshire Terrier");
        razaPerros.add("Dalmata");
        razaPerros.add("Boxer");
        razaPerros.add("Boxer");
        razaPerros.add("Chihuahua");
        razaPerros.add("Buldog Ingles");
        razaPerros.add("Beagle");
        razaPerros.add("otros");

        //GATOS
        razaGatos = new ArrayList<>();
        razaGatos.add("Siames");
        razaGatos.add("Angora");
        razaGatos.add("Persa");
        razaGatos.add("Gato Azul");
        razaGatos.add("British");
        razaGatos.add("otros");
        //CONEJOS
        razaConejos = new ArrayList<>();
        razaConejos.add("otros");
        //HAMSTER
        razaHamster = new ArrayList<>();
        razaHamster.add("otros");


        llenarSpinnerE();
        //recuperando cod_especie de spinner y llenando spinner de raza
        especie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                              @Override
                                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                  codigoE = especieMascotasAux.get(position).codEspecie;
                                                  //Toast.makeText(getApplicationContext(), codigoE, Toast.LENGTH_SHORT).show();
                                                  nombreE = especieMascotasAux.get(position).nomEspecie;
                                                  if (nombreE.equals("Perro")) {
                                                      ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, razaPerros);
                                                      raza.setAdapter(adapter);
                                                  }
                                                  if (nombreE.equals("Gato")) {
                                                      ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, razaGatos);
                                                      raza.setAdapter(adapter);
                                                  }
                                                  if (nombreE.equals("Conejo")) {
                                                      ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, razaConejos);
                                                      raza.setAdapter(adapter);
                                                  }
                                                  if (nombreE.equals("Hamster")) {
                                                      ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, razaHamster);
                                                      raza.setAdapter(adapter);
                                                  }

                                              }

                                              @Override
                                              public void onNothingSelected(AdapterView<?> parent) {

                                              }
                                          }
        );
        //recuperando datos de Dni y Cliente de listaMascotas.class
        dni.setText(getIntent().getStringExtra("dni"));
        cliente.setText(getIntent().getStringExtra("cliente"));


    }

    public void llenarSpinnerE() {
        try {
            basedatos = accesoBasedatos.getInstance(getApplicationContext());
            basedatos.open();
            especieMascotasAux = new ArrayList<>();
            especieMascotasAux = basedatos.llenarSpinnerEspecieM();
            basedatos.close();
            especieString = new ArrayList();
            for (int x = 0; x < especieMascotasAux.size(); x++) {
                especieString.add(especieMascotasAux.get(x).nomEspecie);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, especieString);
            especie.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    public void registrarNuevaMasc(View view) {
        basedatos = accesoBasedatos.getInstance(getApplicationContext());
        try {
            basedatos.open();
            //recuperando datos para enviar a registrar
            codigoM = basedatos.generarCodMascota();
            dniString = dni.getText().toString();
            nombreString = nombreM.getText().toString();
            if(nombreM.getText().toString().isEmpty()){
                nombreM.setError("Ingrese nombre de mascota");
            }
            razaString = raza.getSelectedItem().toString();
            sexoString = sexo.getSelectedItem().toString();
            String edadString = edadM.getText().toString();
            if(edadString.isEmpty())
                edadM.setError("Ingrese edad");
            edadDouble = Double.parseDouble(edadString);
            periodoString = periodo.getSelectedItem().toString();
            if(periodoString.equals("Meses")){
                edadDouble = edadDouble/12;
            }
            //Toast.makeText(getApplicationContext(),String.valueOf(edadDouble),Toast.LENGTH_SHORT).show();
            int dniInt = Integer.parseInt(dniString);
            basedatos.insertarMascota(codigoM,codigoE,dniInt,nombreString,razaString,sexoString,edadDouble);
            Toast.makeText(getApplicationContext(),"Se registro Mascota con exito",Toast.LENGTH_SHORT).show();
            basedatos.close();
            nombreM.setText("");
            edadM.setText("");
            nombreM.requestFocus();

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

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

}
