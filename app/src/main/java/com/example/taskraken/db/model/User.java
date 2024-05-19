package com.example.taskraken.db.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.taskraken.network.schemas.users.UserRead;

import java.util.UUID;

@Entity(tableName="users")
public class User {
    @PrimaryKey()
    @NonNull
    @ColumnInfo(name="ID")
    public UUID id;
    public String email;
    public String password;
    public Boolean isActive;
    public Boolean isSuperuser;
    public Boolean isVerified;
    public String username;
    @ColumnInfo(defaultValue = "false")
    private Boolean picked;

    public void setPicked(Boolean picked) {
        this.picked = picked;
    }

    public User(
            @NonNull UUID id,
            String password,
            String email,
            Boolean isActive,
            Boolean isSuperuser,
            Boolean isVerified,
            String username
    ){
        this.id = id;
        this.password = password;
        this.email = email;
        this.isActive = isActive;
        this.isSuperuser = isSuperuser;
        this.isVerified = isVerified;
        this.username = username;
        pick();
    }

    public User(@NonNull UserRead user){
        this(
            user.getId(),
            user.getPassword(),
            user.getEmail(),
            user.getActive(),
            user.getSuperuser(),
            user.getVerified(),
            user.getUsername()
        );
    }

    public void pick(){
        this.picked = true;
    }

    public void unpick(){
        this.picked = false;
    }

    public Boolean getPicked() {
        return picked;
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
