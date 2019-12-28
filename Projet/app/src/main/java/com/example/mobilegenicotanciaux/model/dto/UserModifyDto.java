package com.example.mobilegenicotanciaux.model.dto;

public class UserModifyDto {

    private String pseudo;
    private String password;
    private String newPassword;
    private String firstName;
    private String lastName;

    public UserModifyDto(String pseudo, String password, String newPassword, String firstName, String lastName) {
        this.pseudo = pseudo;
        this.password = password;
        this.newPassword = newPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserModifyDto() {}

    public String getPseudo() {
        return pseudo;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
