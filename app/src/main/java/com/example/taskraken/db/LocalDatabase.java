package com.example.taskraken.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskraken.db.model.User;


@Database(entities = {User.class}, version = 1)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract User userDao();
}
