package com.example.mobilegenicotanciaux.services;

import com.example.mobilegenicotanciaux.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {

    @POST("AppUser/add")
    Call<User> addUser(@Query("accessCode") String accessCode, @Body User user);
}
