package com.example.taskraken.network.schemas.users;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class UserUpdate {
    @SerializedName("id")
    @Expose
    private UUID id;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

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

    public String getPassword() {
        return password;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public void setSuperuser(Boolean superuser) {
        isSuperuser = superuser;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
