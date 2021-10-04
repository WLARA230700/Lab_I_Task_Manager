package com.willy_ronald.lab_i_task_manager.Modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

public class DB extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "LabITaskManager.bd";
    private static final int VERSION_BD = 1;
    private static final String TABLA_TAREAS = "CREATE TABLE TAREAS (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NOMBRE TEXT NOT NULL, DESCRIPCION TEXT NOT NULL, FECHA TEXT NOT NULL, HORA TEXT NOT NULL, CATEGORIA TEXT NOT NULL)";

    private static final String tbTareas = "TAREAS";

    public DB(Context context) {
        super(context, NOMBRE_BD, null, VERSION_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_TAREAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + tbTareas);
    }

//--------------------------------------------------------------------------------------------------
//-------------------------------------- TASK METHODS ----------------------------------------------
//--------------------------------------------------------------------------------------------------
    public boolean agregarTarea(Tarea tarea){

        SQLiteDatabase db = getWritableDatabase();

        if (tarea != null){
            String nombre = tarea.getNombre();
            String descripcion = tarea.getDescripcion();
            String fecha = tarea.getFecha();
            String hora = String.valueOf(tarea.getHora());
            String categoria = tarea.getCategoria();

            if (db != null){
                db.execSQL("INSERT INTO TAREAS (NOMBRE, DESCRIPCION, FECHA, HORA, CATEGORIA) VALUES ('"+nombre+"', '"+descripcion+"', '"+fecha+"', '"+hora+"', '"+categoria+"')");
                db.close();
                return true;
            }
        }
        return false;
    }// Fin agregarTarea

//--------------------------------------------------------------------------------------------------
    public ArrayList<Tarea> getTareas(){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS", null);

        cursor.moveToFirst();

        ArrayList<Tarea> list = new ArrayList<>();

        while (!cursor.isAfterLast()){
            Tarea tarea = new Tarea();
            tarea.setId(cursor.getInt(0));
            tarea.setNombre(cursor.getString(1));
            tarea.setDescripcion(cursor.getString(2));
            tarea.setFecha(cursor.getString(3));
            tarea.setHora(cursor.getString(4));
            tarea.setCategoria(cursor.getString(5));
            list.add(tarea);
            cursor.moveToNext();
        }// Fin del while

        cursor.close();
        return list;
    }// Fin getTareas
//--------------------------------------------------------------------------------------------------

    public boolean modificarTarea(Tarea tarea){
        SQLiteDatabase db = getReadableDatabase();

        if(tarea != null){
            int id = tarea.getId();
            String nombre = tarea.getNombre();
            String descripcion = tarea.getDescripcion();
            String fecha = tarea.getFecha();
            String hora = tarea.getHora();
            String categoria = tarea.getCategoria();

            if (db != null){
                Cursor cursor = db.rawQuery("SELECT * FROM TAREAS WHERE ID = '"+id+"'", null);
                cursor.moveToFirst();

                Tarea tareaBD = new Tarea();

                if (cursor.getCount() != 0){
                    tareaBD.setId(cursor.getInt(0));
                    tareaBD.setNombre(cursor.getString(1));
                    tareaBD.setDescripcion(cursor.getString(2));
                    tareaBD.setFecha(cursor.getString(3));
                    tareaBD.setHora(cursor.getString(4));
                    tareaBD.setCategoria(cursor.getString(5));
                }
                if (id == tareaBD.getId()){
                    db = getWritableDatabase();
                    db.execSQL("UPDATE TAREAS SET NOMBRE = '"+nombre+"',  DESCRIPCION = '"+descripcion+"',  FECHA = '"+fecha+
                            "',  HORA = '"+hora+"',  CATEGORIA = '"+categoria+"' WHERE ID = '"+id+"'");
                    db.close();
                    return true;
                }
            }
        }

        return false;
    }
//--------------------------------------------------------------------------------------------------
    public ArrayList<Tarea> getTareasPorCategoria(String categoria){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM TAREAS WHERE CATEGORIA = '"+categoria+"'", null);

        cursor.moveToFirst();

        ArrayList<Tarea> list = new ArrayList<>();

        while (!cursor.isAfterLast()){
            Tarea tarea = new Tarea();
            tarea.setId(cursor.getInt(0));
            tarea.setNombre(cursor.getString(1));
            tarea.setDescripcion(cursor.getString(2));
            tarea.setFecha(cursor.getString(3));
            tarea.setHora(cursor.getString(4));
            tarea.setCategoria(cursor.getString(5));
            list.add(tarea);
            cursor.moveToNext();
        }// Fin del while

        cursor.close();
        return list;
    }// Fin getTareas
//--------------------------------------------------------------------------------------------------
//--------------------------------------------------------------------------------------------------
}// FIn Clase
