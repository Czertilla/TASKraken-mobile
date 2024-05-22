package com.example.taskraken.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskraken.db.model.Cookie;

import java.util.List;

@Dao
public interface CookieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Cookie cookie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Cookie... cookies);

    @Query("SELECT * FROM cookies")
    List<Cookie> getAllCookies();

    @Query("select * from cookies where cookies.ID = :id")
    List<Cookie> getById(String id);

    @Query("select * from cookies where cookies.name = :name")
    List<Cookie> getByName(String name);

    @Update
    public void updateCookie(Cookie cookie);

    @Update
    public void updateCookie(Cookie... cookies);

    @Delete
    public void delete(Cookie cookie);
}