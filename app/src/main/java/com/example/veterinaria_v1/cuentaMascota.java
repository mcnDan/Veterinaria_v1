package com.example.veterinaria_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;

public class cuentaMascota extends AppCompatActivity {
ListView lvPrueba;
ScrollView miScroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta_mascota);
        lvPrueba = findViewById(R.id.lv_prueba);
        miScroll = findViewById(R.id.miScrollview);
        ArrayList<String> razaPerros = new ArrayList<>();
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
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,razaPerros);
        lvPrueba.setAdapter(adapter);




        miScroll.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                findViewById(R.id.lv_prueba).getParent().requestDisallowInterceptTouchEvent(false);
                return  false;
            }
        });
        lvPrueba.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;

            }
        });
    }
}