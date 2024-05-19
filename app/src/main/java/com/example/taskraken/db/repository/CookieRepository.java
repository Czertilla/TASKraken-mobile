package com.example.taskraken.db.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.example.taskraken.db.LocalDatabase;
import com.example.taskraken.db.model.Cookie;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CookieRepository {
    private static CookieRepository cookieRepository;
    LocalDatabase localDatabase;

    private CookieRepository(Context context){
        super();
        localDatabase = Room.databaseBuilder(
                context,
                LocalDatabase.class,
                "localDB"
        ).allowMainThreadQueries().build();
    }

    public static CookieRepository setInstance(Context context){
        cookieRepository = new CookieRepository(context);
        return cookieRepository;
    }

    public static CookieRepository getInstance(){
        return cookieRepository;
    }

    public void insert(Cookie cookie) {
        localDatabase.cookieDao().insertOne(cookie);
    }

    public void insert(Cookie... cookies){
        localDatabase.cookieDao().insertAll(cookies);
    }

    public void insert(List<okhttp3.Cookie> cookies) {
        cookies.forEach(cookie -> localDatabase.cookieDao().insertOne(new Cookie(cookie)));
    }

    public void insert(okhttp3.Cookie cookieHeader){
        Cookie cookie = new Cookie(cookieHeader);
        insert(cookie);
    }

    public List<okhttp3.Cookie> getAllCookies() {
        List<Cookie> cookies = localDatabase.cookieDao().getAllCookies();
        List<okhttp3.Cookie> result = new ArrayList<>();
        cookies.forEach(cookie -> result.add(cookie.getCookie()));
        return result;
    }

    public okhttp3.Cookie getById(String domain, String name) {
        String id = domain + '@' + name;
        return localDatabase.cookieDao().getById(id).get(0).getCookie();
    }

    public okhttp3.Cookie getOneByName(String name) {
        return localDatabase.cookieDao().getByName(name).get(0).getCookie();
    }

    public void update(Cookie cookie) {
        localDatabase.cookieDao().updateCookie(cookie);
    }

    public void update(okhttp3.Cookie cookieHeader){
        update(new Cookie(cookieHeader));
    }

    public void update(Cookie... cookies){
        localDatabase.cookieDao().updateCookie(cookies);
    }

}
