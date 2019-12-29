package com.example.mobilegenicotanciaux.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobilegenicotanciaux.R;
import com.example.mobilegenicotanciaux.model.TrainingSchool;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FormationAdapter extends RecyclerView.Adapter<FormationAdapter.ViewHolder> {

    private ArrayList<TrainingSchool> formations;

    public FormationAdapter(ArrayList<TrainingSchool> formations) {
        this.formations = formations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_formation, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TrainingSchool formation = formations.get(position);
        holder.titre.setText(formation.getTraining().getName());
        holder.description.setText(formation.getTraining().getDescription());
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        String output = holder.date.getText() + " " + myFormat.format(formation.getDateTraining());
        holder.date.setText(output);
    }

    @Override
    public int getItemCount() {
        return formations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView titre;
        public TextView description;
        public TextView date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titre = itemView.findViewById(R.id.input_titre);
            description = itemView.findViewById(R.id.input_description);
            date = itemView.findViewById(R.id.input_date);

        }
    }
}
