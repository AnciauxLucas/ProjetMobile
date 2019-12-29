package com.example.mobilegenicotanciaux.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.mobilegenicotanciaux.R;
import com.example.mobilegenicotanciaux.model.Formation;
import com.example.mobilegenicotanciaux.model.TrainingSchool;
import com.example.mobilegenicotanciaux.utils.FormationAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormationActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formation);
        ButterKnife.bind(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<TrainingSchool> formations = (ArrayList<TrainingSchool>) getIntent().getExtras().getSerializable("formations");
        RecyclerView.Adapter adapter = new FormationAdapter(formations);
        recyclerView.setAdapter(adapter);
    }
}
