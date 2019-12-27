package com.example.mobilegenicotanciaux.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.mobilegenicotanciaux.R;
import com.example.mobilegenicotanciaux.model.Planting;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlantationActivity extends AppCompatActivity {

    @BindView(R.id.vegetable)
    public ImageView vegetable;
    @BindView(R.id.input_vegetable)
    public TextView inputVegetable;
    @BindView(R.id.input_description)
    public TextView inputDescription;
    @BindView(R.id.input_planted)
    public TextView input_planted;
    @BindView(R.id.input_price)
    public TextView input_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation);
        ButterKnife.bind(this);
        OnBack
        Glide.with(PlantationActivity.this).load(R.drawable.vegetable).into(vegetable);
        Planting planting = (Planting)getIntent().getExtras().getSerializable("planting");
        inputVegetable.setText(planting.getVegetable().getName());
        inputDescription.setText(planting.getVegetable().getDescription());
        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy");
        input_planted.setText(myFormat.format(planting.getDatePlanted()));
        input_price.setText(String.format("%.2f", planting.getVegetable().getPrice()) + "€");
    }
}
