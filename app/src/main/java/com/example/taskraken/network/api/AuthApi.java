package com.example.taskraken.network.api;

import com.example.taskraken.network.schemas.auth.VerifyRequest;
import com.example.taskraken.network.schemas.auth.VerifyTokenRequest;
import com.example.taskraken.network.schemas.users.UserCreate;
import com.example.taskraken.network.schemas.users.UserRead;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {
    String prefix = "/auth";

    @FormUrlEncoded
    @POST(prefix+"/jwt/login")
    Call<UserRead> login(
            @Field("username") String email,
            @Field("password") String password
    );

    @POST(prefix+"/register")
    Call<UserCreate> register(
            @Body UserCreate userCreate
    );

    @POST(prefix+"/request-verify-token")
    Call<Object> requestToken(
            @Body VerifyTokenRequest verifyTokenRequest
    );

    @POST(prefix+"/verify")
    public  Call<Object> verify(
            @Body VerifyRequest verifyRequest
    );

    @POST(prefix+"/jwt/logout")
    public Call<Object> logout();
}
