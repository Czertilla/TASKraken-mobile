package com.example.taskraken.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.taskraken.db.model.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("select 1 from user where user.id = :id")
    User getById(UUID id);

    @Update
    public void updateUser(User user);

    @Query("select 1 from user where user.picked = 1")
    User getPickedUser();

}
