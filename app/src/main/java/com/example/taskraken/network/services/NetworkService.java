package com.example.taskraken.network.services;

import com.example.taskraken.network.api.AuthApi;
import com.example.taskraken.network.api.ProjectsApi;
import com.example.taskraken.network.api.RolesApi;
import com.example.taskraken.network.api.StructsApi;
import com.example.taskraken.network.api.TasksApi;
import com.example.taskraken.network.api.UsersApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static NetworkService mInstance;
    private static final String BASE_URL = "http://10.0.2.2:8000";
//    private static final String BASE_URL = "https://taskraken.czertilla.ru";
    private final Retrofit mRetrofit;

    private NetworkService() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .cookieJar(new CookieService());

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public UsersApi getUserApi() {
        return mRetrofit.create(UsersApi.class);
    }

    public AuthApi getAuthApi() {
        return mRetrofit.create(AuthApi.class);
    }

    public TasksApi getTaskApi() {
        return mRetrofit.create(TasksApi.class);
    }

    public StructsApi getStructApi(){return mRetrofit.create(StructsApi.class);}

    public RolesApi getRoleApi(){return mRetrofit.create(RolesApi.class);}

    public ProjectsApi getProjectApi() {return mRetrofit.create(ProjectsApi.class);}
    public String getBaseUrl(){
        return BASE_URL;
    }

}