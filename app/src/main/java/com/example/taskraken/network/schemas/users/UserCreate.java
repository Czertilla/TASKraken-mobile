package com.example.taskraken.network.schemas.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;


public class UserCreate {
    @SerializedName("id")
    @Expose
    private UUID id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @SerializedName("is_active")
    @Expose
    private Boolean isActive;

    @SerializedName("is_superuser")
    @Expose
    private Boolean isSuperuser;

    @SerializedName("is_verified")
    @Expose
    private Boolean isVerified;

    @SerializedName("username")
    @Expose
    private String username;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Boolean getSuperuser() {
        return isSuperuser;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public String getUsername() {
        return username;
    }


}
