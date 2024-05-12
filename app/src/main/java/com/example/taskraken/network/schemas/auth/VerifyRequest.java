package com.example.taskraken.network.schemas.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyRequest {
    @SerializedName("token")
    @Expose
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
