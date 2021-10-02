package com.willy_ronald.lab_i_task_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.willy_ronald.lab_i_task_manager.Modelo.DB;
import com.willy_ronald.lab_i_task_manager.Modelo.Tarea;

import java.io.CharArrayReader;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class act_modificar_tarea extends AppCompatActivity {

    CardView btnBack, btnModificar;

    EditText txtNombre, txtHora, txtFecha, txtDescripcion;
    Spinner spinnerCategorias;

    String[] categorias = {"Trabajo", "Doméstica", "Universidad"};
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_modificar_tarea);

        final DB database = new DB(getApplicationContext());

        Bundle bundle = getIntent().getExtras();
        Tarea tarea = (Tarea) bundle.getSerializable("tareaSeleccionada");

        int id = tarea.getId();

        btnBack = findViewById(R.id.btnBack);
        btnModificar = findViewById(R.id.btnModificar);

        spinnerCategorias = findViewById(R.id.spinnerCategorias);

        asignarCategoriasSpinner();

        txtNombre = findViewById(R.id.txtNombre);
        txtHora = findViewById(R.id.txtHora);
        txtFecha = findViewById(R.id.txtFecha);
        txtDescripcion = findViewById(R.id.txtDescripcion);


        txtNombre.setText(tarea.getNombre());
        txtFecha.setText(tarea.getFecha().toString());
        txtHora.setText(tarea.getHora().toString());
        txtDescripcion.setText(tarea.getDescripcion());
        spinnerCategorias.setSelection(adapter.getPosition(tarea.getCategoria()));

        Toast.makeText(getApplicationContext(), tarea.toString(), Toast.LENGTH_LONG).show();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!txtNombre.getText().equals("") && !txtHora.getText().equals("")
                        && !txtFecha.getText().equals("") && !txtDescripcion.getText().equals("")){


                    String charHora = txtHora.getText().toString();

                    int h = Integer.parseInt(charHora.charAt(0) + "" + charHora.charAt(1));
                    int m = Integer.parseInt(charHora.charAt(3) + "" + charHora.charAt(4));
                    int s = Integer.parseInt(charHora.charAt(6) + "" + charHora.charAt(7));

                    if (charHora.toCharArray().length == 8){
                        if (charHora.charAt(2) == ':' && charHora.charAt(5) == ':'){

                            if (h < 24 && m <= 59 && s <= 59){
                                Date fecha = null;
                                try {
                                    fecha = new SimpleDateFormat("dd/MM/yyyy").parse(txtFecha.getText().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                String hora = txtHora.getText().toString();

                                Tarea tareaMOD = new Tarea(id, txtNombre.getText().toString(), txtDescripcion.getText().toString(),
                                        fecha, hora, spinnerCategorias.getSelectedItem().toString());

                                database.modificarTarea(tareaMOD);

                                clear();
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(), "Hora inválida", Toast.LENGTH_LONG).show();
                                txtHora.setText("");
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Debe cumplir el formato: \nhh:mm:ss", Toast.LENGTH_LONG).show();
                            txtHora.setText("");
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Debe cumplir el formato: \nhh:mm:ss", Toast.LENGTH_LONG).show();
                        txtHora.setText("");
                    }
                }
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