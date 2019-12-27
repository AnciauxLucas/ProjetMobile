package com.example.mobilegenicotanciaux.utils;

import com.example.mobilegenicotanciaux.model.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {

    private static String BASE_URL = "https://ugardenwebapi.azurewebsites.net/";
    private static Gson gson;
    private static Retrofit retrofitWithoutToken;
    private static Retrofit retrofit;
    private static Token token;

    public static Retrofit getRetrofitWithoutToken() {
        if (retrofitWithoutToken == null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();
            retrofitWithoutToken = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitWithoutToken;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            OkHttpClient.Builder okhttpBuilder = new OkHttpClient.Builder();
            okhttpBuilder.addInterceptor(chain -> {
                Request request = chain.request();
                Request.Builder newRequest = request.newBuilder().addHeader("Authorization", "Bearer " + token.getAccess_token());
                return chain.proceed(newRequest.build());
            });

            retrofit = new Retrofit.Builder()
                    .client(okhttpBuilder.build())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static Token getToken() {
        return token;
    }

    public static void setToken(Token token) {
        RetrofitFactory.token = token;
    }
}
