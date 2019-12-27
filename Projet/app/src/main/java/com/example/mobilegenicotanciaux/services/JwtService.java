package com.example.mobilegenicotanciaux.services;

import com.example.mobilegenicotanciaux.model.Jwt;
import com.example.mobilegenicotanciaux.model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JwtService {

    @POST("Jwt")
    Call<Token> getToken(@Body Jwt jwt);

}
