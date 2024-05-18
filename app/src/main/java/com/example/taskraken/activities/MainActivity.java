package com.example.taskraken.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.taskraken.R;
import com.example.taskraken.db.dao.UserDao;
import com.example.taskraken.db.model.User;
import com.example.taskraken.db.repository.UserRepository;
import com.example.taskraken.network.api.AuthApi;
import com.example.taskraken.network.services.NetworkService;
import com.example.taskraken.network.api.UsersApi;
import com.example.taskraken.network.schemas.users.UserRead;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView debugTextView;
    UsersApi usersApi;
    NetworkService networkService;
    UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userRepository = new UserRepository();
        setUpNetwork();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuth();
    }

    private void setUpNetwork(){
        networkService = NetworkService.getInstance();
        usersApi = networkService.getUserApi();
        debugTextView = this.findViewById(R.id.debugTextView);
    }

    private void setUpSideMenu(){

    }

    private void checkAuth(){
        usersApi.getCurrentUser().enqueue(new Callback<UserRead>() {
            @Override
            public void onResponse(
                    @NonNull Call<UserRead> call,
                    @NonNull Response<UserRead> response
            ) {
                debugTextView.setText(response.headers().toMultimap().toString());
                debugTextView.append("\n"+response.code());
                if (!response.isSuccessful()) {
                    switch (response.code()) {
                        case 401: {// Unauthorized
                            Intent loginIntent = new Intent(
                                    MainActivity.this,
                                    LoginActivity.class);
                            if (!getUserFromCache())
                                MainActivity.this.startActivity(loginIntent);
                            break;
                        }
                        case 403: {// Forbidden
                            Intent verifyIntent = new Intent(
                                    MainActivity.this,
                                    VerifyActivity.class
                            );
                            startActivity(verifyIntent);
                            break;
                        }
                        default: {
                            if (response.errorBody() != null) {
                                try {
                                    debugTextView.append(new JSONObject(
                                            response.errorBody().string()
                                            )
                                            .toString()
                                    );
                                } catch (JSONException | IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            break;
                        }
                    }
                }
                else {
                    UserRead user = response.body();
                    assert user != null;
                    debugTextView.append("\n" + user.getId());
                    debugTextView.append("\n" + user.getUsername());
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(
                    @NonNull Call<UserRead> call,
                    @NonNull Throwable t
            ) {
                debugTextView.setText("Error occurred while getting request!");
                t.printStackTrace();

            }
        });
    }

    private boolean getUserFromCache(){
        User user = userRepository.getPickedUser();
        if (user == null){
            return false;
        }
        final Boolean[] result = new Boolean[1];
        networkService.getAuthApi().login(
                user.email,
                user.password
        ).enqueue(new Callback<UserRead>() {
            @Override
            public void onResponse(Call<UserRead> call, Response<UserRead> response) {
                result[0] = response.isSuccessful();
            }

            @Override
            public void onFailure(Call<UserRead> call, Throwable t) {
                Log.e("", "");
                result[0] = false;
            }
        });
        return result[0];
    }
}