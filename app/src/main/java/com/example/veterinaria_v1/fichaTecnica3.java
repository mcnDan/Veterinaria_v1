package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class fichaTecnica3 extends AppCompatActivity {
    private Intent i;
    private EditText otrosExamenes,observacionesE;
    private String codigoM, tvDni, tvCliente, motivo, triaje, descripcion,hallazgosFt2, hallazgosC,pruebasAuxiliares;
    //CHECKBOX EXAMENES
    private CheckBox ecografiaE,radiografiaE,resonanciaE,electrocardioE,
    //CHECKBOX ANALISIS LABORATORIO
    raspdepielA,hemogramaA,tpvA,tdpA,hecesA,antibiogramaA,autorizacionA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_tecnica3);
        //validando EDITTEXT EXAMENES
        otrosExamenes = findViewById(R.id.ml_otrosExam);
        observacionesE = findViewById(R.id.ml_obsExamenes);
        //validando CHECKBOX EXAMENES
        ecografiaE = findViewById(R.id.chk_ecografiaExa);
        resonanciaE = findViewById(R.id.chk_resonanciaExa);
        radiografiaE = findViewById(R.id.chk_radiografiaExa);
        electrocardioE = findViewById(R.id.chk_electroExa);
        //validadndo CHECKBOX ANALISIS LABORATORIO
        raspdepielA = findViewById(R.id.chk_raspielAnalisis);
        hemogramaA = findViewById(R.id.chk_hemogramaAnalisis);
        tpvA = findViewById(R.id.chk_testpvAnalisis);
        tdpA = findViewById(R.id.chk_testdpAnalisis);
        hecesA = findViewById(R.id.chk_hecesAnalisis);
        antibiogramaA = findViewById(R.id.chk_antibioAnalisis);
        autorizacionA = findViewById(R.id.chk_autorizacion);
        //recibiendo datos de fichaTecnia2.class
        if(getIntent()!=null){
            codigoM = getIntent().getStringExtra("codigoM");
            tvDni = getIntent().getStringExtra("dni");
            tvCliente = getIntent().getStringExtra("cliente");
            motivo = getIntent().getStringExtra("motivo");
            triaje =getIntent().getStringExtra("triaje");
            descripcion = getIntent().getStringExtra("anamnesis");
            hallazgosC = getIntent().getStringExtra("hallazgosC");


        }
    }
    //botonos superiores
    public void goInicio(View view) {
        i = new Intent(this, MainActivity.class);
        i.putExtra("dni", tvDni);
        startActivity(i);
    }

    public void goListaMascotas(View view) {
        i = new Intent(this, listaMascotas.class);
        i.putExtra("cliente", tvCliente);
        i.putExtra("dni", tvDni);
        startActivity(i);
    }
    //boton inferior
    public void siguiente(View view){
        reportePruebasAuxiliares();
        //Toast.makeText(getApplicationContext(),triaje+hallazgosC+pruebasAuxiliares,Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this,fichaTecnica4.class);
        //enviando datos para tabla CONSULTA
        i.putExtra("codigoM",codigoM);
        i.putExtra("motivo",motivo);
        i.putExtra("triaje",triaje);
        i.putExtra("anamnesis",descripcion);
        i.putExtra("hallazgosC",hallazgosC);
        i.putExtra("pruebasA",pruebasAuxiliares);
        //enviando datos para botones HOME y LISTAMASCOTAS
        i.putExtra("dni",tvDni);
        i.putExtra("cliente",tvCliente);

        startActivity(i);

    }
    //generando reporte PRUEBAS AUXILIARES
    public void reportePruebasAuxiliares(){
        //almacenando en Array para generar  reporte con formato basico.
        ArrayList<String> auxExamenes = new ArrayList<>();
        pruebasAuxiliares="\nPRUEBAS AUXILIARES" +
                "\n==================" +
                "\nExamenes: ";
        if(ecografiaE.isChecked())
            auxExamenes.add(ecografiaE.getText().toString());
        if(resonanciaE.isChecked())
            auxExamenes.add(resonanciaE.getText().toString());
        if(radiografiaE.isChecked())
            auxExamenes.add(radiografiaE.getText().toString());
        if(electrocardioE.isChecked())
            auxExamenes.add(electrocardioE.getText().toString());
        for(int x =0 ; x<auxExamenes.size();x++)
        {
            if(x==auxExamenes.size()-1)
                pruebasAuxiliares+=auxExamenes.get(x).toString()+".";
            else{
                pruebasAuxiliares+=auxExamenes.get(x).toString()+", ";
            }
        }
        pruebasAuxiliares+="\nOtr.Examenes: "+otrosExamenes.getText().toString();
        pruebasAuxiliares+="\nObservaciones: "+observacionesE.getText().toString()+
        "\nPruebasLaboratorio: ";
        //recogiendo datos de Analisis de labortorio
        ArrayList<String> auxlaboratorio = new ArrayList<>();
        if(raspdepielA.isChecked())
            auxlaboratorio.add(raspdepielA.getText().toString());
        if(hemogramaA.isChecked())
            auxlaboratorio.add(hemogramaA.getText().toString());
        if(tpvA.isChecked())
            auxlaboratorio.add(tpvA.getText().toString());
        if(tdpA.isChecked())
            auxlaboratorio.add(tdpA.getText().toString());
        if(hecesA.isChecked())
            auxlaboratorio.add(hecesA.getText().toString());
        if(antibiogramaA.isChecked())
            auxlaboratorio.add(antibiogramaA.getText().toString());

        for(int x = 0 ; x < auxlaboratorio.size();x++){
            if(x==auxlaboratorio.size()-1)
                pruebasAuxiliares+=auxlaboratorio.get(x).toString()+".";
            else
                pruebasAuxiliares+=auxlaboratorio.get(x).toString()+", ";
        }
        if(autorizacionA.isChecked())
            pruebasAuxiliares+="\nNotaImportante: Por orden del dueño no se realizo ningun examen.";

    }
}