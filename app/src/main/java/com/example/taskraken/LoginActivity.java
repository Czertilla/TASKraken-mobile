package com.example.taskraken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.taskraken.network.api.AuthApi;
import com.example.taskraken.network.schemas.users.UserRead;
import com.example.taskraken.network.services.NetworkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends Activity {
    Button loginButton;
    TextView registerButton;
    TextView debugView;
    EditText email;
    EditText password;
    NetworkService networkService;
    AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = this.findViewById(R.id.buttonLogin);
        registerButton = this.findViewById(R.id.gotoRegisterButton);
        debugView = this.findViewById(R.id.debugTextView);
        email = this.findViewById(R.id.emailLoginField);
        password = this.findViewById(R.id.passwordLogin);
        networkService = NetworkService.getInstance();

        authApi = networkService.getAuthApi();

        loginButton.setOnClickListener(v -> {
            authApi.login(
                    email.getText().toString(),
                    password.getText().toString()
                    )
                .enqueue(new Callback<UserRead>() {
                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) {
                        debugView.setText(response.headers().toMultimap().toString());
                        debugView.append("\n"+response.code());
                        if (!response.isSuccessful() & response.errorBody() != null) {
                            try {
                                debugView.append( new JSONObject(response.errorBody().string())
                                        .toString()
                                );
                            } catch (JSONException | IOException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (response.isSuccessful()) {
                            LoginActivity.this.finish();
                        }
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull Throwable t) {

                        debugView.setText("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
        });

        registerButton.setOnClickListener(v -> {
            Intent registryIntent = new Intent(
              LoginActivity.this,
              RegisterActivity.class
            );
            LoginActivity.this.startActivity(registryIntent);
        });
    }
}