package com.example.mobilegenicotanciaux.services;

import com.example.mobilegenicotanciaux.model.Planting;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PlantingService {

    @GET("Planting/{id}")
    Call<Planting> getPlanting(@Path("id") Integer id);
}
