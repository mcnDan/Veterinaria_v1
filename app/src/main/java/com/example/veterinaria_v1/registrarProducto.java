package com.example.veterinaria_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import veterinaria_v1.basedatosConeccion.accesoBasedatos;

public class registrarProducto extends AppCompatActivity {
    private ImageView imgProducto;
    private String codProducto;
    private EditText nomProducto, costoProducto, precioProducto, cantidadProducto,descripProducto;
    private accesoBasedatos basedatos;
    private Intent i;
    private int REQUEST_IMAGE_CAMERA=100;
    private int REQUEST_PERMISSION_CAMERA=101;
    private Bitmap bitmapProducto;
    private String currentPhothoPath;
    private String imgProducto64;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_producto);
        //validando variables
        imgProducto = findViewById(R.id.iv_producto);
        nomProducto = findViewById(R.id.et_nomProducto);
        costoProducto = findViewById(R.id.et_costoProducto);
        precioProducto = findViewById(R.id.et_precioProducto);
        cantidadProducto = findViewById(R.id.et_cantidadProducto);
        descripProducto = findViewById(R.id.et_descripProRP);
    }
    // BOTON TOMAR FOTO
    public void takePicture(View view){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission
                    (registrarProducto.this, Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
                openCamera();
            }
            else{
                ActivityCompat.requestPermissions
                        (registrarProducto.this, new String[] {Manifest.permission.CAMERA}, REQUEST_PERMISSION_CAMERA);

            }
        }
        else{
            openCamera();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
          if( requestCode ==REQUEST_PERMISSION_CAMERA){
              if(permissions.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                  openCamera();
              }
              else
                  Toast.makeText(getApplicationContext(),"Necesita habilitar permisos",Toast.LENGTH_SHORT).show();
          }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_IMAGE_CAMERA){
            if(resultCode == RESULT_OK){
                //bitmapProducto = (Bitmap) data.getExtras().get("data");
                //imgProducto.setImageBitmap(bitmapProducto);
                imgProducto.setImageURI(Uri.parse(currentPhothoPath));
            }

        }

    }
    public void openCamera(){
        Intent camIntent  = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(camIntent.resolveActivity(getPackageManager())!=null){
            //startActivityForResult(camIntent, REQUEST_IMAGE_CAMERA);
            File photofile = null;
            try {
                photofile = createFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ( photofile != null){
                Uri photoUri = FileProvider.getUriForFile(
                        this,
                        "com.example.veterinaria_v1",
                        photofile
                );
                camIntent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(camIntent,REQUEST_IMAGE_CAMERA);
            }

        }
    }

    private File createFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyymmdd_HHmm-ss", Locale.getDefault()).format(new Date());
        String imgFilename = "IMG_"+ timeStamp+"_";
        File storeDir =getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imgFilename,
                ".jpeg",
                storeDir
        );
        currentPhothoPath = image.getAbsolutePath();
        return image;
    }

    public void registrarProducto(View view){
        //OPEN BASE DATOS
        basedatos = accesoBasedatos.getInstance(getApplicationContext());
        try {
            basedatos.open();
            //RECOGIENDO VALORES PARA INSERTAR
            codProducto = basedatos.generarCodProducto();
            //int ancho= 700;
            //nt alto = 800;
            //opcion 1
            /*BitmapDrawable drawable = (BitmapDrawable) imgProducto.getDrawable();
            Bitmap imgProduBitmap = drawable.getBitmap();*/
            Bitmap bitmap = ((BitmapDrawable) imgProducto.getDrawable()).getBitmap();
             //bitmapProducto = Bitmap.createScaledBitmap(bitmap,ancho,alto,true);
            if (bitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                // original es bitmap en ves de bitmapProdcuto
                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);

                byte[] byteArray = byteArrayOutputStream.toByteArray();
                imgProducto64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
            }


            //opcion3
         /*   ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imgProduBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);*/
            //opcion 2
            /*ByteArrayOutputStream baos = new ByteArrayOutputStream(20480);
            bitmapProducto.compress(Bitmap.CompressFormat.PNG, 95 , baos);
            byte[] blob = baos.toByteArray();*/
            basedatos.insertarProducto(codProducto,nomProducto.getText().toString(),
                    Double.parseDouble(costoProducto.getText().toString()),
                    Double.parseDouble(precioProducto.getText().toString()),
                    Integer.parseInt(cantidadProducto.getText().toString()),
                    descripProducto.getText().toString(), imgProducto64);
            basedatos.close();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("|-Producto Registrado-|");
            builder.setMessage(String.format("¿Desea registrar un nuevo producto?"));
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    i = new Intent(getApplicationContext(),opcionesUsuario.class);
                    startActivity(i);
                }
            });
            builder.setPositiveButton("Si",null);
            builder.create().show();
            nomProducto.setText("");
            costoProducto.setText("");
            precioProducto.setText("");
            cantidadProducto.setText("");
            descripProducto.setText("");
            imgProducto.setImageResource(android.R.mipmap.sym_def_app_icon);

            Toast.makeText(this, "Registro con exito", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
            basedatos.close();
        }
    }



}