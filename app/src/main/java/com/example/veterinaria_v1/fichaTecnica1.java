package com.example.veterinaria_v1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class fichaTecnica1 extends AppCompatActivity {

    private Intent i;
    //TEXTVIEW INFORMACION PRINCIPAL PARA EL LLENADO DE TABLAS
    private TextView tvCliente, tvDni, tvNombreM, tvCodigoM;
    //EDITTEXT- TRIAJE
    private EditText etMotivo, etFecha, etTemp, etPeso, etAnamnesis, etOtros;
    // RADIOBUTTON TRIAJE
    private RadioButton rbSi, rbNo;
    //RADIOBUTTON HALLAZGOS CLLINICOS
    private RadioButton rbFiebreAlta,rbFiebreRegular, rbPresionBaja,rbDefecNormal,rbDefecDiarrea,
            rbDefecBlanco,rbDefecRojo,rbDefecCafe,rbDefecNegro,rbEscalofrioFuerte,rbEscalofrioLento;
    private CheckBox chk_cf, chk_hp, chk_dt, chk_pv, chk_rf, chk_pf, chk_lp, chk_pi, chk_rb;
    // STRING AUXILIARES TRIAJE, ANAMNESIS y HALLAZGOS CLINICOS
    private String triajeString,descripcionString, hallazgosClinicos_string;
    private double costo_double;
    private accesoBasedatos basedatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_tecnica1);
        //validacion TEXVIEW cliente y mascotas -TRIAJE -> tabla base de datos
        tvCliente = findViewById(R.id.tv_nombreCLiente);
        tvDni = findViewById(R.id.tv_dniF1);
        tvNombreM = findViewById(R.id.tv_nombreMasc);
        tvCodigoM = findViewById(R.id.tv_codigoMasc);
        //validacion EDTITEXT Y RADIOBUTTON - TRIAJE
        etMotivo = findViewById(R.id.ml_motivoConsulta);
        etFecha = findViewById(R.id.et_fecha);
        etTemp = findViewById(R.id.et_temp);
        etPeso = findViewById(R.id.et_peso);
        etOtros = findViewById(R.id.ml_otrosVacuna);
        etAnamnesis = findViewById(R.id.ml_anamnesisD);
        rbSi = findViewById(R.id.rb_siVacunado);
        rbNo = findViewById(R.id.rb_noVacunado);
        //validacion CHECKBOX - TRIAJE
        chk_cf = findViewById(R.id.chk_CFvacuna);
        chk_hp = findViewById(R.id.chk_HPvacuna);
        chk_dt = findViewById(R.id.chk_DTvacuna);
        chk_pv = findViewById(R.id.chk_PVvacuna);
        chk_rf = findViewById(R.id.chk_RFvacuna);
        chk_pf = findViewById(R.id.chk_PFvacuna);
        chk_lp = findViewById(R.id.chk_LPvacuna);
        chk_pi = findViewById(R.id.chk_PIvacuna);
        chk_rb = findViewById(R.id.chk_RBvacuna);
        //validando RADIOBUTTON  de -Hallazgos Clinicos-
        //-T°Corporal
        rbFiebreAlta = findViewById(R.id.rb_fiebreAltaTemp);
        rbFiebreRegular= findViewById(R.id.rb_regularTemp);
        rbPresionBaja = findViewById(R.id.rb_preBajaTemp);
        //-Defecaciones
        rbDefecNormal = findViewById(R.id.rb_normalDefec);
        rbDefecDiarrea = findViewById(R.id.rb_diarreaDefec);
        rbDefecBlanco= findViewById(R.id.rb_blanqueDefec);
        rbDefecRojo = findViewById(R.id.rb_rojizoDefec);
        rbDefecCafe = findViewById(R.id.rb_cafeDefec);
        rbDefecNegro = findViewById(R.id.rb_negroDefec);
        //-Escalofrios
        rbEscalofrioFuerte = findViewById(R.id.rb_fuerteEscalofrios);
        rbEscalofrioLento = findViewById(R.id.rb_lentoEscalofrio);

        //recibiendo datos cliente y mascota de listaMascotas.class
        try {
            if (getIntent() != null) {
                tvCliente.setText(getIntent().getStringExtra("cliente"));
                tvDni.setText(getIntent().getStringExtra("dni"));
                tvNombreM.setText(getIntent().getStringExtra("nombreM"));
                tvCodigoM.setText(getIntent().getStringExtra("codigoM"));
                //obteniendo hora actual
                Date ahora = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                etFecha.setText(format.format(ahora));
                //controlando radiobutton vacuna : Si - NO
                chk_cf.setEnabled(false);
                chk_hp.setEnabled(false);
                chk_dt.setEnabled(false);
                chk_pv.setEnabled(false);
                chk_rf.setEnabled(false);
                chk_pf.setEnabled(false);
                chk_lp.setEnabled(false);
                chk_pi.setEnabled(false);
                chk_rb.setEnabled(false);

                rbSi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chk_cf.setEnabled(true);
                        chk_hp.setEnabled(true);
                        chk_dt.setEnabled(true);
                        chk_pv.setEnabled(true);
                        chk_rf.setEnabled(true);
                        chk_pf.setEnabled(true);
                        chk_lp.setEnabled(true);
                        chk_pi.setEnabled(true);
                        chk_rb.setEnabled(true);
                    }
                });
                rbNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        chk_cf.setEnabled(false);
                        chk_hp.setEnabled(false);
                        chk_dt.setEnabled(false);
                        chk_pv.setEnabled(false);
                        chk_rf.setEnabled(false);
                        chk_pf.setEnabled(false);
                        chk_lp.setEnabled(false);
                        chk_pi.setEnabled(false);
                        chk_rb.setEnabled(false);
                    }
                });


            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void goInicio(View view) {
        i = new Intent(this, MainActivity.class);
        i.putExtra("dni", tvDni.getText().toString());
        startActivity(i);
    }

    public void goListaMascotas(View view) {
        i = new Intent(this, listaMascotas.class);
        i.putExtra("cliente", tvCliente.getText().toString());
        i.putExtra("dni", tvDni.getText().toString());
        startActivity(i);
    }

    public void siguiente(View view) {
        hallazgosClinicos_string="";
         /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle(R.string.confirmar);
         builder.setMessage(String.format("¿Pasar a la siguiente pantalla?"));
         builder.setPositiveButton(R.string.continuar, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {*/
                 reporteTriaje();
                 reporteHallazgosClinicos();
                 Toast.makeText(getApplicationContext(),triajeString+ hallazgosClinicos_string, Toast.LENGTH_LONG).show();
                 i = new Intent(getApplicationContext(),fichaTecnica2.class);
                 //enviando datos para tabla CONSULTA
                 i.putExtra("codigoM",tvCodigoM.getText().toString());
                 i.putExtra("triaje",triajeString);
                 i.putExtra("anamnesis",descripcionString);
                 i.putExtra("hallazgosC",hallazgosClinicos_string);
                 //enviando datos para botones HOME y LISTAMASCOTAS
                 i.putExtra("dni",tvDni.getText().toString());
                 i.putExtra("cliente",tvCliente.getText().toString());
                 startActivity(i);
            /* }
         });
         builder.setNegativeButton(R.string.Cancelar,null);
         builder.create().show();*/
    }

    public void reporteTriaje() {
        // llenando Triaje - Formato muy simple.
        triajeString = "REPORTE TRIAJE" +
                "\n =====================" +
                "\nNombreCliente: " + tvCliente.getText().toString() +
                "\nDNI: " + tvDni.getText().toString() +
                "\nNombre Mascota: " + tvNombreM.getText().toString() +
                "\nMotivo Consulta: " + etMotivo.getText().toString() +
                "\nFecha: " + etFecha.getText().toString() +
                "\nTemperatura: " + etTemp.getText().toString() +
                "\nPeso: " + etPeso.getText().toString();
        //condicional radiobutton
        if (rbSi.isChecked())
            triajeString += "\nVacunado: " + rbSi.getText().toString() +
                    "\nTipoVacuna: ";
        else
            triajeString += "\nVacunado: " + rbNo.getText().toString() +
                    "\nTipoVacuna: ";
        //condicionales para checkbox
        //utilizando array para ordenar datos de checkbox
        ArrayList<String> auxCheckBox = new ArrayList<>();
        if (chk_cf.isChecked())
            auxCheckBox.add(chk_cf.getText().toString());
        if (chk_hp.isChecked())
            auxCheckBox.add(chk_hp.getText().toString());
        if (chk_dt.isChecked())
            auxCheckBox.add(chk_dt.getText().toString());
        if (chk_pv.isChecked())
            auxCheckBox.add(chk_pv.getText().toString());
        if (chk_rf.isChecked())
            auxCheckBox.add(chk_rf.getText().toString());
        if (chk_pf.isChecked())
            auxCheckBox.add(chk_pf.getText().toString());
        if (chk_lp.isChecked())
            auxCheckBox.add(chk_lp.getText().toString());
        if (chk_pi.isChecked())
            auxCheckBox.add(chk_pi.getText().toString());
        if (chk_rb.isChecked())
            auxCheckBox.add(chk_rb.getText().toString());
        for (int x = 0; x < auxCheckBox.size(); x++) {
            if (x == auxCheckBox.size() - 1)
                triajeString += auxCheckBox.get(x) + ".";
            else
                triajeString += auxCheckBox.get(x).toString() + ", ";
        }
        //agregando Otros y Anamnesis - descripcion
        triajeString += "\nOtrasVacunas: " + etOtros.getText().toString() ;
        descripcionString = etAnamnesis.getText().toString();

    }
    public void reporteHallazgosClinicos(){
        hallazgosClinicos_string = "\nHALLAZGOS CLINICOS"+
                "\n============================";
        if(rbFiebreAlta.isChecked())
            hallazgosClinicos_string += "\nT°Corporal: "+rbFiebreAlta.getText().toString();
        if(rbFiebreRegular.isChecked())
            hallazgosClinicos_string += "\nT°Corporal: "+rbFiebreRegular.getText().toString();
        if(rbPresionBaja.isChecked())
            hallazgosClinicos_string += "\nT°Corporal: "+rbPresionBaja.getText().toString();
        //Condicionales para juntar Defecaciones Tipo - Color
        //-defecaciones normal
        if(rbDefecNormal.isChecked() && rbDefecBlanco.isChecked())
            hallazgosClinicos_string+="\nDefecaciones: "+rbDefecNormal.getText().toString()+"-"+rbDefecBlanco.getText().toString();
        if(rbDefecNormal.isChecked() && rbDefecRojo.isChecked())
            hallazgosClinicos_string+="\nDefecaciones: "+rbDefecNormal.getText().toString()+"-"+rbDefecRojo.getText().toString();
        if(rbDefecNormal.isChecked() && rbDefecCafe.isChecked())
            hallazgosClinicos_string+="\nDefecaciones: "+rbDefecNormal.getText().toString()+"-"+rbDefecCafe.getText().toString();
        if(rbDefecNormal.isChecked() && rbDefecNegro.isChecked())
            hallazgosClinicos_string+="\nDefecaciones: "+rbDefecNormal.getText().toString()+"-"+rbDefecNegro.getText().toString();
        //-defecaciones diarrea
        if(rbDefecDiarrea.isChecked() && rbDefecBlanco.isChecked())
            hallazgosClinicos_string+="\nDefecaciones: "+rbDefecDiarrea.getText().toString()+"-"+rbDefecBlanco.getText().toString();
        if(rbDefecDiarrea.isChecked() && rbDefecRojo.isChecked())
            hallazgosClinicos_string+="\nDefecaciones: "+rbDefecDiarrea.getText().toString()+"-"+rbDefecRojo.getText().toString();
        if(rbDefecDiarrea.isChecked() && rbDefecCafe.isChecked())
            hallazgosClinicos_string+="\nDefecaciones: "+rbDefecDiarrea.getText().toString()+"-"+rbDefecCafe.getText().toString();
        if(rbDefecDiarrea.isChecked() && rbDefecNegro.isChecked())
            hallazgosClinicos_string+="\nDefecaciones: "+rbDefecDiarrea.getText().toString()+"-"+rbDefecNegro.getText().toString();
        //condicionales para Escalofrios
        if(rbEscalofrioFuerte.isChecked())
            hallazgosClinicos_string+="\nEscalofrios: "+rbEscalofrioFuerte.getText().toString();
        if(rbEscalofrioLento.isChecked())
            hallazgosClinicos_string+="\nEscalofrios: "+rbEscalofrioLento.getText().toString();


    }
}