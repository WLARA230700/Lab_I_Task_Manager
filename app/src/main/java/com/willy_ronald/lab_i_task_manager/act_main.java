package com.willy_ronald.lab_i_task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.willy_ronald.lab_i_task_manager.Modelo.AdapterTaskList;
import com.willy_ronald.lab_i_task_manager.Modelo.DB;
import com.willy_ronald.lab_i_task_manager.Modelo.Tarea;

import java.util.ArrayList;
import java.util.Arrays;

public class act_main extends AppCompatActivity {

    Spinner spinnerCategorias;
    ListView listViewTareas;
    CardView btnAgregarTarea;

    String[] categorias = {"Trabajo", "Doméstica", "Universidad"};

    ArrayAdapter adapter;
    AdapterTaskList adapterTaskList;
    ArrayList<Tarea> tareas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_main);

        final DB database = new DB(getApplicationContext());

        spinnerCategorias = findViewById(R.id.spinnerCategorias);
        btnAgregarTarea = findViewById(R.id.btn_nueva_tarea);
        listViewTareas = findViewById(R.id.listViewTareas);
        btnAgregarTarea = findViewById(R.id.btn_nueva_tarea);

        asignarCategoriasSpinner();

        actualizarLista(database);

        btnAgregarTarea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent agregar = new Intent(act_main.this, act_agregar_tarea.class);
                startActivity(agregar);
            }
        });

        listViewTareas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                seleccionarTarea(database, i);
            }
        });

    }//Fin onCreate

    @Override
    protected void onRestart() {
        super.onRestart();
        final DB database = new DB(getApplicationContext());
        actualizarLista(database);
    }

    public void seleccionarTarea(DB database, int i){

        tareas = database.getTareas();

        Tarea tarea = tareas.get(i);

        if(tarea != null){
            Intent modificar = new Intent(act_main.this, act_modificar_tarea.class);

            Bundle objetoEnviado = new Bundle();
            objetoEnviado.putSerializable("tareaSeleccionada", tarea);

            modificar.putExtras(objetoEnviado);

            startActivity(modificar);
        }else{
            Toast.makeText(getApplicationContext(), "Error al cargar la Tarea", Toast.LENGTH_SHORT).show();
        }
    }//Fin seleccionarTarea

    public void asignarCategoriasSpinner(){
        ArrayList<String> categoriasList = new ArrayList<>(Arrays.asList(categorias));
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,categoriasList);
        spinnerCategorias.setAdapter(adapter);
    }//Fin asignarCategoriasSpinner

    public void actualizarLista(DB database){
        tareas = database.getTareas();

        if(!tareas.isEmpty()){
            adapterTaskList = new AdapterTaskList(getApplicationContext(), database.getTareas());
            listViewTareas.setAdapter(adapterTaskList);
        }
    }//Fin actualizarLista
}