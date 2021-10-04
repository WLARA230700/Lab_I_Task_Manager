package com.willy_ronald.lab_i_task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.timepicker.TimeFormat;
import com.willy_ronald.lab_i_task_manager.Modelo.DB;
import com.willy_ronald.lab_i_task_manager.Modelo.DatePiker;
import com.willy_ronald.lab_i_task_manager.Modelo.Tarea;
import com.willy_ronald.lab_i_task_manager.Modelo.TimePiker;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class act_agregar_tarea extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    CardView btnBack, btnAgregar;
    EditText txtNombre, txtDescripcion;
    TextView txtFecha, txtHora;
    Spinner spinnerCategorias;
    ImageView btnCalendar, btnHora;

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

        btnCalendar = findViewById(R.id.btnCalendar);
        btnHora = findViewById(R.id.btnHora);

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

                if (!txtNombre.getText().equals("") && !txtHora.getText().equals("Seleccione una hora")
                        && !txtFecha.getText().equals("Seleccione una fecha") && !txtDescripcion.getText().equals("")){

                    Tarea tarea = new Tarea(txtNombre.getText().toString(), txtDescripcion.getText().toString(),
                            txtFecha.getText().toString(), txtHora.getText().toString(), spinnerCategorias.getSelectedItem().toString());

                    database.agregarTarea(tarea);
                    Toast.makeText(getApplicationContext(), "Agregada correctamente", Toast.LENGTH_SHORT).show();
                    clear();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "Complete los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePiker = new DatePiker();
                datePiker.show(getSupportFragmentManager(), "Date piker");
            }
        });

        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePiker = new TimePiker();
                timePiker.show(getSupportFragmentManager(), "Time piker");
            }
        });

    }//Fin onCreate

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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        String fechaSeleccionada = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        txtFecha.setText(fechaSeleccionada);
    }


    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);

        String horaSeleccionada = DateFormat.getTimeInstance(DateFormat.MEDIUM).format(calendar.getTime());
        txtHora.setText(horaSeleccionada);
    }
}