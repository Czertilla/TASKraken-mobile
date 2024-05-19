package com.example.taskraken.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskraken.db.dao.CookieDao;
import com.example.taskraken.db.dao.UserDao;
import com.example.taskraken.db.model.Cookie;
import com.example.taskraken.db.model.User;


@Database(
        entities = {
                User.class,
                Cookie.class
        },
        version = 1
)
public abstract class LocalDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract CookieDao cookieDao();
}
