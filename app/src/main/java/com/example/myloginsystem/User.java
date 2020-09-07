package com.example.myloginsystem;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    private String id;

    @SerializedName("token")
    private String token;

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
