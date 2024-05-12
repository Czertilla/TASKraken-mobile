package com.example.taskraken.network.api;

import com.example.taskraken.network.schemas.users.UserRead;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UsersApi {
    static String prefix = "/users";

    @GET(prefix+"/{id}")
    public Call<UserRead> getUser(@Path("id") UUID id);

    @GET(prefix+"/me")
    public Call<UserRead> getCurrentUser();
}
