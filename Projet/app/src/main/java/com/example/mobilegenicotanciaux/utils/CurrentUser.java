package com.example.mobilegenicotanciaux.utils;

import com.example.mobilegenicotanciaux.model.User;
import com.example.mobilegenicotanciaux.model.dto.UserDto;
import com.example.mobilegenicotanciaux.services.UserService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CurrentUser {

    private static UserDto currentUser;

    public static Boolean getCurrentUserFromApi() {
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        UserService userService = retrofit.create(UserService.class);
        Call<UserDto> call = userService.getUser();
        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful()) {
                    CurrentUser.setCurrentUser(response.body());
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {

            }
        });
        return currentUser == null;
    }

    public static UserDto getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserDto currentUser) {
        CurrentUser.currentUser = currentUser;
    }
}
