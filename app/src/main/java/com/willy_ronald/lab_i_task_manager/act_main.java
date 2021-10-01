package com.willy_ronald.lab_i_task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class act_main extends AppCompatActivity {

    Spinner spinnerCategorias;
    CardView btn_nueva_tarea;

    String[] categorias = {"Trabajo", "Doméstica", "Universidad"};

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_main);
        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        btn_nueva_tarea = findViewById(R.id.btn_nueva_tarea);

        asignarCategoriasSpinner();

        btn_nueva_tarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), act_agregar_tarea.class);
                startActivity(intent);
            }
        });

    }//Fin onCreate

    public void asignarCategoriasSpinner(){
        ArrayList<String> categoriasList = new ArrayList<>(Arrays.asList(categorias));
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,categoriasList);
        spinnerCategorias.setAdapter(adapter);
    }
}