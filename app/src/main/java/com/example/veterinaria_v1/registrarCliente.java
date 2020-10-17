package com.example.veterinaria_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class registrarCliente extends AppCompatActivity {
    private static final int REQUEST_CODE_AGENDA = 42;
    private accesoBasedatos basedatos;
    private Intent intent;
    private EditText dniC, nombreC, apellidoC, telefonoC, correC, direccionC;
    private Button regCel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_registrar_cliente);
        dniC = findViewById(R.id.et_dni);
        nombreC = findViewById(R.id.et_nombresCliente);
        apellidoC = findViewById(R.id.et_apellido);
        telefonoC = findViewById(R.id.et_telefono);
        correC = findViewById(R.id.et_correo);
        direccionC = findViewById(R.id.et_direccion);
        regCel = findViewById(R.id.bt_registrarCel);
        dniC.requestFocus();

    }

    public void goInicio(View view) {
        intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void registrarClienteEnBasedatos(View view) {
        // Creates a new Intent to insert a contact

            try{
            basedatos = accesoBasedatos.getInstance(getApplicationContext());
            basedatos.open();
            String dni_string = dniC.getText().toString();
            String codigoPersona = basedatos.generarCodPersona();
            String ape_string = apellidoC.getText().toString();
            String nom_string = nombreC.getText().toString();
            String tel_string = telefonoC.getText().toString();
            String corr_string = correC.getText().toString();
            String dir_string = direccionC.getText().toString();
        basedatos.insertarPersona(codigoPersona,ape_string,nom_string,tel_string,corr_string,dir_string);
        int dni_int = Integer.parseInt(dniC.getText().toString());
            boolean verificarDni_aux = basedatos.verificarDNI(dni_int);
            if (verificarDni_aux == false) {
                basedatos.insertarCliente(dni_string, codigoPersona);
                regCel.setEnabled(true);
                basedatos.close();
                Toast.makeText(getApplicationContext(), "Datos Registrados con exito", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"DNI ya existe en la base de datos",Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
                if(dniC.getText().toString().isEmpty())
                    dniC.setError("Ingrese DNI");
            Toast.makeText(getApplicationContext(), "No se registro cliente", Toast.LENGTH_SHORT).show();
        }


    }
    public void registrarClienteEnCel(View view){
        String nomApe = null;
        nomApe = nombreC.getText().toString() + " " + apellidoC.getText().toString();
        intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        // Sets the MIME type to match the Contacts Provider
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, correC.getText())
                //intent.putExtra((ContactsContract.Groups.ACCOUNT_NAME,"vetsalud"))

                /** In this example, sets the email type to be a work email.
                 * You can set other email types as necessary.*/

                .putExtra(ContactsContract.Intents.Insert.NAME, nomApe)
                .putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_HOME)
                // Inserts a phone number
                .putExtra(ContactsContract.Intents.Insert.PHONE, telefonoC.getText())
                .putExtra(ContactsContract.Groups.TITLE,"vetsalud")

                /** In this example, sets the phone type to be a work phone.
                 * You can set other phone types as necessary.*/

                .putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);


        startActivityForResult(intent,REQUEST_CODE_AGENDA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.regContacto);
        builder.setMessage(String.format("¿Desea registrar otro cliente?"));
        builder.setNegativeButton(R.string.rb_no_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("dni",dniC.getText().toString());
                startActivity(intent);
            }
        });
        builder.setPositiveButton(R.string.rb_si_string, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dniC.setText("");
                nombreC.setText("");
                apellidoC.setText("");
                telefonoC.setText("");
                correC.setText("");
                direccionC.setText("");
                regCel.setEnabled(false);
                dniC.requestFocus();
            }
        });
        builder.create().show();

    }
}