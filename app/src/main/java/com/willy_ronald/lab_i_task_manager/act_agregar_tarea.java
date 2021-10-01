package com.willy_ronald.lab_i_task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.willy_ronald.lab_i_task_manager.Modelo.DB;
import com.willy_ronald.lab_i_task_manager.Modelo.Tarea;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class act_agregar_tarea extends AppCompatActivity {

    CardView btnBack, btnAgregar;
    EditText txtNombre, txtHora, txtFecha, txtDescripcion;
    Spinner spinnerCategorias;

    String[] categorias = {"Trabajo", "Doméstica", "Universidad"};
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_agregar_tarea);

        final DB database = new DB(getApplicationContext());

        btnBack = findViewById(R.id.btnBack);
        btnAgregar = findViewById(R.id.btnAgregar);

        spinnerCategorias = findViewById(R.id.spinnerCategorias);

        txtNombre = findViewById(R.id.txtNombre);
        txtHora = findViewById(R.id.txtHora);
        txtFecha = findViewById(R.id.txtFecha);
        txtDescripcion = findViewById(R.id.txtDescripcion);

        asignarCategoriasSpinner();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!txtNombre.getText().equals("") && !txtHora.getText().equals("")
                        && !txtFecha.getText().equals("") && !txtDescripcion.getText().equals("")){

                    Date fecha = null;
                    try {
                        fecha = new SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Time hora = Time.valueOf(txtHora.getText().toString());

                    Tarea tarea = new Tarea(txtNombre.getText().toString(), txtDescripcion.getText().toString(),
                            fecha, hora, spinnerCategorias.getSelectedItem().toString());

                    database.agregarTarea(tarea);
                }

                clear();
                finish();
            }
        });

    }

    public void clear(){
        txtNombre.setText("");
        txtHora.setText("");
        txtFecha.setText("");
        txtDescripcion.setText("");
    }

    public void asignarCategoriasSpinner(){
        ArrayList<String> categoriasList = new ArrayList<>(Arrays.asList(categorias));
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,categoriasList);
        spinnerCategorias.setAdapter(adapter);
    }//Fin asignarCategoriasSpinner
}