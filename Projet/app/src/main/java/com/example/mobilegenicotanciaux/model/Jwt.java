package com.example.mobilegenicotanciaux.model;

public class Jwt {

    private String userName;
    private String password;

    public Jwt(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
