package com.example.otp;

public class User {

    private String Password, name, Mobile, Image;

    public User() {

    }

    public User(String Password, String name, String Mobile, String Image) {
        this.Password = Password;
        this.name = name;
        this.Mobile = Mobile;
        this.Image = Image;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return Mobile;
    }

    public void setPhoneNumber(String Mobile) {
        this.Mobile =Mobile;
    }

    public String Image() {
        return Image;
    }

    public void setProfileImage(String profileImage) {
        this.Image = Image;
    }
}