package com.example.taskraken.network.schemas.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class VerifyTokenRequest {
    @SerializedName("email")
    @Expose
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
