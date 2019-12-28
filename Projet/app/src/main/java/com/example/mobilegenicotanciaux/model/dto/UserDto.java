package com.example.mobilegenicotanciaux.model.dto;

public class UserDto {

    private String pseudo;
    private String firstName;
    private String lastName;
    private String teacherCode;

    public UserDto(String pseudo, String firstName, String lastName, String teacherCode) {
        this.pseudo = pseudo;
        this.firstName = firstName;
        this.lastName = lastName;
        if (teacherCode != null) this.teacherCode = teacherCode;
    }

    public UserDto() {}

    public String getPseudo() {
        return pseudo;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

}


