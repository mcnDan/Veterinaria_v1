package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class fichaTecnica4 extends AppCompatActivity {
    private Intent i;
    private accesoBasedatos basedatos;
    //CHECKBOX DIAGNOSTICO
    private CheckBox generalDiag, presuntivoDiag, diferenciaDiag, confirmadoDiag, requiereDiag;
    private EditText etDiagnostico, etDescripicionTratDiag;
    private String codigoM, tvDni, tvCliente, triaje, descripcion, hallazgosC, pruebasAuxiliares, diagnostico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ficha_tecnica4);
        //disable Edittext "tratamiento a realizar"

        //validando Checkbox
        generalDiag = findViewById(R.id.chk_generalDiag);
        presuntivoDiag = findViewById(R.id.chk_presuntivoGene);
        diferenciaDiag = findViewById(R.id.chk_diferencialGen);
        confirmadoDiag = findViewById(R.id.chk_confirmadoGen);
        requiereDiag = findViewById(R.id.chk_requiereTratamiento);
        //validando EditText
        etDiagnostico = findViewById(R.id.ml_diagnostico);
        etDescripicionTratDiag = findViewById(R.id.ml_tratamientoDiag);
        etDescripicionTratDiag.setEnabled(false);
        //recibiendo datos de fichaTecnia2.class
        if (getIntent() != null) {
            codigoM = getIntent().getStringExtra("codigoM");
            tvDni = getIntent().getStringExtra("dni");
            tvCliente = getIntent().getStringExtra("cliente");
            triaje = getIntent().getStringExtra("triaje");
            descripcion = getIntent().getStringExtra("anamnesis");
            hallazgosC = getIntent().getStringExtra("hallazgosC");
            pruebasAuxiliares = getIntent().getStringExtra("pruebasA");
            //enable/ disable
            requiereDiag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (requiereDiag.isChecked())
                        etDescripicionTratDiag.setEnabled(true);
                    else {
                        etDescripicionTratDiag.setEnabled(false);
                        etDescripicionTratDiag.setText("");
                    }
                }
            });
        }
    }

    public void registrarConsulta(View view) {
        reporteDiagnostico();
        Toast.makeText(getApplicationContext(), triaje + hallazgosC + pruebasAuxiliares + diagnostico, Toast.LENGTH_SHORT).show();
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle(R.string.confirmarR);
         builder.setMessage(String.format("Confirmar Registro de datos"));
         builder.setPositiveButton(R.string.confirmar, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 basedatos = accesoBasedatos.getInstance(getApplicationContext());
                 basedatos.open();
                 String codigoC = basedatos.generarCodConsulta();
                 String fecha = basedatos.getFechahoy();

                 //Toast.makeText(getApplicationContext(),codigoC,Toast.LENGTH_LONG).show();
                 boolean auxRequiere;
                 if (requiereDiag.isChecked())
                     auxRequiere = true;
                 else
                     auxRequiere = false;

                 basedatos.insertarConsulta(codigoC, codigoM, fecha, triaje, descripcion, hallazgosC,
                         pruebasAuxiliares, diagnostico,
                         etDescripicionTratDiag.getText().toString(), auxRequiere);
                 basedatos.close();
                 i = new Intent(getApplicationContext(),listaMascotas.class);
                 i.putExtra("cliente",tvCliente);
                 i.putExtra("dni",tvDni);
                 startActivity(i);
             }
         });
            builder.setNegativeButton(R.string.Cancelar,null);
            builder.create().show();



        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            System.out.println(e.getMessage());
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

    //generar reporte
    public void reporteDiagnostico() {
        ArrayList<String> aux = new ArrayList<>();
        diagnostico = "\nDIAGNOSTICO" +
                "\n=====================" +
                "\nDiagnostico: ";
        if (generalDiag.isChecked())
            aux.add(generalDiag.getText().toString());
        if (presuntivoDiag.isChecked())
            aux.add(presuntivoDiag.getText().toString());
        if (diferenciaDiag.isChecked())
            aux.add(diferenciaDiag.getText().toString());
        if (confirmadoDiag.isChecked())
            aux.add(confirmadoDiag.getText().toString());
        for (int x = 0; x < aux.size(); x++) {
            if (x == aux.size() - 1)
                diagnostico += aux.get(x).toString() + ".";
            else
                diagnostico += aux.get(x).toString() + ", ";
        }
        diagnostico += "\nDescripcion: " + etDiagnostico.getText().toString();
        if (requiereDiag.isChecked()) {
            diagnostico += "\nNotaImportante: Requiere tratamiento";
            diagnostico += "\nDescripcionTrat. : " + etDescripicionTratDiag.getText().toString();
        }

    }

    //boton inferior

}