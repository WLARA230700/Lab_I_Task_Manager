package com.willy_ronald.lab_i_task_manager.Modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.willy_ronald.lab_i_task_manager.R;

import java.util.ArrayList;

public class AdapterTaskList extends BaseAdapter {

    Context context;
    ArrayList<Tarea> listaTareas;

    public AdapterTaskList(Context context, ArrayList<Tarea> listaTareas){
        this.context = context;
        this.listaTareas = listaTareas;
    }

    @Override
    public int getCount() {
        return listaTareas.size();
    }

    @Override
    public Object getItem(int i) {
        return listaTareas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.task_item, null);
        }

        TextView taskName = view.findViewById(R.id.taskName);
        TextView taskCategory = view.findViewById(R.id.taskCategory);

        taskName.setText(listaTareas.get(i).getNombre());
        taskCategory.setText(listaTareas.get(i).getCategoria());

        return view;
    }
}
