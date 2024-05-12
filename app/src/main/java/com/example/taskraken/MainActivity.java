package com.example.taskraken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkService = NetworkService.getInstance();
        usersApi = networkService.getUserApi();
        debugTextView = this.findViewById(R.id.debugTextView);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
    }

    private void checkLogin(){
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
                                    debugTextView.append(new JSONObject(response.errorBody().string())
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
}