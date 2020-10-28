package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class fichaTecnica2 extends AppCompatActivity {
    Intent i;
    //RADIOBUTTON VOMITOS
    private RadioButton siVomitos, noVomitos, frecuenteVomitos, regularVomitos, espumosoVomitos,
    //RADIOBUTTON COLOR
    amariVerdosoColorV, rojoColorV,
    //RADIOBUTTON AGITACION PULMONAR
    rapidaAgitacion, regularAgitacion, lentoAgitacion,
    //RADIOBUTTON ESTORNUDOS
    fuerteEstornudo, regularEstornudo,
    //RADIOBUTTON TRAQUEA
    irritacionTraquea, regularTraque,
    //RADIOBUTTON SECREC. MUCOSA
    narizSecrecion, bocaSecrecion;
    private EditText otros;
    private String codigoM, tvDni, tvCliente,motivo, triaje, descripcion,hallazgosFt1, hallazgosC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_tecnica2);
        //validando RADIOBUTTON
        //-vomitos / color
        siVomitos = findViewById(R.id.rb_siVomitos);
        noVomitos = findViewById(R.id.rb_noVomitos);
        frecuenteVomitos = findViewById(R.id.rb_frecuentesVom);
        regularVomitos = findViewById(R.id.rb_regularVomi);
        espumosoVomitos = findViewById(R.id.rb_espumosoVom);
        amariVerdosoColorV = findViewById(R.id.rb_amariverColor);
        rojoColorV = findViewById(R.id.rb_rojoColor);
        //-RADIOBUTTON / AGITACION
        rapidaAgitacion = findViewById(R.id.rb_rapidaPulm);
        regularAgitacion = findViewById(R.id.regularPulm);
        lentoAgitacion = findViewById(R.id.lentaPulm);
        //-RADIOBUTTON / ETORNUDOS
        fuerteEstornudo = findViewById(R.id.rb_fuertesEstorn);
        regularEstornudo = findViewById(R.id.rb_regularEstornudo);
        //-RADIOBUTTON / TRAQUEA
        irritacionTraquea = findViewById(R.id.rb_irritacionTraquea);
        regularTraque = findViewById(R.id.rb_regularTraquea);
        //-RADIOBUTTON / SECRECION MUCOSA
        narizSecrecion = findViewById(R.id.rb_narizSecre);
        bocaSecrecion = findViewById(R.id.rb_bocaSecre);
        //EDITTEXT multilane (no nombrado) editTextTextMultiLine = edittextOtros
        otros = findViewById(R.id.editTextTextMultiLine);



        //desabilitando RADIOBUTTON para eleccion
        frecuenteVomitos.setEnabled(false);
        regularVomitos.setEnabled(false);
        espumosoVomitos.setEnabled(false);
        amariVerdosoColorV.setEnabled(false);
        rojoColorV.setEnabled(false);

        if (getIntent() != null) {
            //Recuperando datos de FICHATECNICA1
            codigoM = getIntent().getStringExtra("codigoM");
            motivo = getIntent().getStringExtra("motivo");
            triaje = getIntent().getStringExtra("triaje");
            descripcion = getIntent().getStringExtra("anamnesis");
            hallazgosFt1 = getIntent().getStringExtra("hallazgosC");
            tvDni = getIntent().getStringExtra("dni");
            tvCliente = getIntent().getStringExtra("cliente");
            // enable / disable  Radiobutton al marcar si o no
            siVomitos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frecuenteVomitos.setEnabled(true);
                    regularVomitos.setEnabled(true);
                    espumosoVomitos.setEnabled(true);
                    amariVerdosoColorV.setEnabled(true);
                    rojoColorV.setEnabled(true);
                }
            });
            noVomitos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    frecuenteVomitos.setEnabled(false);
                    regularVomitos.setEnabled(false);
                    espumosoVomitos.setEnabled(false);
                    amariVerdosoColorV.setEnabled(false);
                    rojoColorV.setEnabled(false);
                }
            });



        }
    }

    //boton inferior
    public void siguiente(View view) {
        hallazgosC="";
        reporteHallazgosClinicos();
        //Toast.makeText(getApplicationContext(),triaje+hallazgosFt1+hallazgosC,Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, fichaTecnica3.class);
        //enviando datos para tabla CONSULTA
        i.putExtra("codigoM",codigoM);
        i.putExtra("motivo",motivo);
        i.putExtra("triaje",triaje);
        i.putExtra("anamnesis",descripcion);
        i.putExtra("hallazgosC",hallazgosFt1+hallazgosC);
        //enviando datos para botones HOME y LISTAMASCOTAS
        i.putExtra("dni",tvDni);
        i.putExtra("cliente",tvCliente);
        startActivity(i);
    }

    public void reporteHallazgosClinicos() {
        //condicionales para extraer selecciones de seccion vomitos y color
        if (noVomitos.isChecked())
            hallazgosC = "\nVomitos: " + noVomitos.getText().toString();
        else {
            ArrayList<String> auxHallagosString = new ArrayList<>();
            if(frecuenteVomitos.isChecked())
                hallazgosC="\nVomitos: "+frecuenteVomitos.getText().toString();
            if(regularVomitos.isChecked())
                hallazgosC+="\nVomitos: "+regularVomitos.getText().toString();
            if(espumosoVomitos.isChecked())
                hallazgosC+="\nVomitos: "+espumosoVomitos.getText().toString();
            if(amariVerdosoColorV.isChecked())
                hallazgosC+="\nColorVomito: "+amariVerdosoColorV.getText().toString();
            if(rojoColorV.isChecked())
                hallazgosC+="\nColorVomito: "+rojoColorV.getText().toString();
                }
        //condicionales para extraer selecciones .Agitacion
        if(rapidaAgitacion.isChecked())
            hallazgosC+="\nAgitacionPul.: "+rapidaAgitacion.getText().toString();
        if(regularAgitacion.isChecked())
            hallazgosC+="\nAgitacionPul.: "+regularAgitacion.getText().toString();
        if(lentoAgitacion.isChecked())
            hallazgosC+="\nAgitacionPul.: "+lentoAgitacion.getText().toString();
        //condicionales para xtraer selecciones de -Estornudos
        if(fuerteEstornudo.isChecked())
            hallazgosC+="\nEstornudo: "+fuerteEstornudo.getText().toString();
        if(regularEstornudo.isChecked())
            hallazgosC+="\nEstornudo: "+regularEstornudo.getText().toString();
        //condicionales para xtraer selecciones de -Traquea
        if(irritacionTraquea.isChecked())
            hallazgosC+="\nTraquea: "+irritacionTraquea.getText().toString();
        if(regularTraque.isChecked())
            hallazgosC+="\nTraquea: "+regularTraque.getText().toString();
        //condicionales para xtraer selecciones de -Secrecion Mucosa
        if(narizSecrecion.isChecked())
            hallazgosC+="\nSecr.Mucosa: "+narizSecrecion.getText().toString();
        if(bocaSecrecion.isChecked())
            hallazgosC+="\nSecr.Mucosa: "+bocaSecrecion.getText().toString();
        hallazgosC+="\nOtros: "+otros.getText().toString();

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
}