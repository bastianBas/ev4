package com.example.bottonnav.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottonnav.R;
import com.example.bottonnav.model.Horarios;

import java.util.ArrayList;

public class HorariosAdapter extends RecyclerView.Adapter<HorariosAdapter.ViewHolder> {

    private int resource;
    private ArrayList<Horarios> horariosList;

    public HorariosAdapter(ArrayList<Horarios> horariosList, int resource){
        this.horariosList = horariosList;
        this.resource = resource;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        Horarios horarios = horariosList.get(index);

        viewHolder.textViewHorarios.setText(horarios.getFecha());
        viewHolder.textViewHorarios.setText(horarios.getHora());

    }

    @Override
    public int getItemCount() {
        return horariosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewHorarios;
        public View view;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            this.textViewHorarios = (TextView)  view.findViewById(R.id.textViewHorarios);
        }
    }


}
