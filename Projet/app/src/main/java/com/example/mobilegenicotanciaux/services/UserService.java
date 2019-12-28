package com.example.mobilegenicotanciaux.services;

import com.example.mobilegenicotanciaux.model.User;
import com.example.mobilegenicotanciaux.model.dto.UserDto;
import com.example.mobilegenicotanciaux.model.dto.UserModifyDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserService {

    @POST("AppUser/add")
    Call<User> addUser(@Query("accessCode") String accessCode, @Body User user);

    @GET("AppUser")
    Call<UserDto> getUser();

    @PUT("AppUser/modify")
    Call<UserModifyDto> modifyUser(@Body UserModifyDto user);
}
