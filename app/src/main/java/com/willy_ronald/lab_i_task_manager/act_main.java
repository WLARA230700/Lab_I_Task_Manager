package com.willy_ronald.lab_i_task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class act_main extends AppCompatActivity {

    Spinner spinnerCategorias;

    String[] categorias = {"Trabajo", "Doméstica", "Universidad"};

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_main);
        spinnerCategorias = findViewById(R.id.spinnerCategorias);

        asignarCategoriasSpinner();

    }//Fin onCreate

    public void asignarCategoriasSpinner(){
        ArrayList<String> categoriasList = new ArrayList<>(Arrays.asList(categorias));
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,categoriasList);
        spinnerCategorias.setAdapter(adapter);
    }
}