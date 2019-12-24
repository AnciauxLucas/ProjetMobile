package com.example.mobilegenicotanciaux.services;

import com.example.mobilegenicotanciaux.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    String BASE_URL = "https://ugardenwebapi.azurewebsites.net/AppUser/";

    @POST("add")
    Call<User> addUser(@Query("accessCode") String accessCode, @Body User user);
}
