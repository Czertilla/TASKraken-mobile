package com.example.taskraken.db.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskraken.network.schemas.users.UserRead;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

@Entity(tableName="user")
public class User {
    @PrimaryKey()
    public UUID id;
    public String email;
    public String password;
    public Boolean isActive;
    public Boolean isSuperuser;
    public Boolean isVerified;
    public String username;
    @ColumnInfo(defaultValue = "false")
    private Boolean picked;

    public User(UserRead user){
        this.id = user.getId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.isActive = user.getActive();
        this.isSuperuser = user.getSuperuser();
        this.isVerified = user.getVerified();
        this.username = user.getUsername();
    }

    public void pick(){
        this.picked = true;
    }

    public void unpick(){
        this.picked = false;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", isActive=" + isActive +
                ", isSuperuser=" + isSuperuser +
                ", isVerified=" + isVerified +
                '}';
    }
}
