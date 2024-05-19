package com.example.taskraken.network.services;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.taskraken.db.repository.CookieRepository;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookieService implements CookieJar {
    CookieRepository cookieRepository;

    public CookieService() {
        super();
        this.cookieRepository = CookieRepository.getInstance();
    }

    @Override
    public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
        cookieRepository.insert(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
        return cookieRepository.getAllCookies();
    }
}