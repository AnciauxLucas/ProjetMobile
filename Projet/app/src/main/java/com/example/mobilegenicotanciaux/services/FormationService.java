package com.example.mobilegenicotanciaux.services;

import com.example.mobilegenicotanciaux.model.TrainingSchool;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FormationService {

    @GET("Training/AllSchoolTraining")
    Call<ArrayList<TrainingSchool>> getFormations();
}
