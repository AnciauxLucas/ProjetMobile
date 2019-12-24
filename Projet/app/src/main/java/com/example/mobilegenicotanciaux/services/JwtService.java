package com.example.mobilegenicotanciaux.services;

import com.example.mobilegenicotanciaux.model.Jwt;
import com.example.mobilegenicotanciaux.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JwtService {
    String BASE_URL = "https://ugardenwebapi.azurewebsites.net/";

    @POST("Jwt")
    Call<User> getToken(@Body Jwt jwt);

}
