package com.example.myloginsystem;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private String id;

    @SerializedName("token")
    private String token;

    @SerializedName("username")
    private String username;

    @SerializedName("gmail")
    private String email;

    @SerializedName("phoneNo")
    private String phone;

    @SerializedName("image_url")
    private String image;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getImage() {
        return image;
    }
}
