package com.example.mobilegenicotanciaux.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    String BASE_URL = "";

    @GET("users")
    Call<User> getUserById(@Query("id") Integer id);
}
