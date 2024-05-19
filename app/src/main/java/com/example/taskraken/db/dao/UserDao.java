package com.example.taskraken.db.dao;

import androidx.lifecycle.LiveData;
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
    void insertOne(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Query("SELECT * FROM users")
    List<User> getAllUsers();

    @Query("select * from users where users.ID = :id")
    List<User> getById(UUID id);

    @Update
    public void updateUser(User user);

    @Update
    public void updateUsers(User... user);

    @Query("select * from users where users.picked = 1")
    List<User> getPickedUser();

}
