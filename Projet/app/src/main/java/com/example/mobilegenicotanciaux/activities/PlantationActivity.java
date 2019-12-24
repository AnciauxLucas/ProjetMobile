package com.example.mobilegenicotanciaux.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mobilegenicotanciaux.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlantationActivity extends AppCompatActivity {

    @BindView(R.id.label_vegetable)
    public TextView labelVegetable;
    @BindView(R.id.vegetable)
    public ImageView vegetable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation);
        ButterKnife.bind(this);
        //Async Task
        //labelVegetable.setText("");
        Glide.with(PlantationActivity.this).load(R.drawable.vegetable).into(vegetable);
    }
}
