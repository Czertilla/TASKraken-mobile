package com.example.taskraken;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.taskraken.network.api.AuthApi;
import com.example.taskraken.network.schemas.users.UserCreate;
import com.example.taskraken.network.services.NetworkService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends Activity {
    Button registryButton;
    EditText emailField;
    EditText passwordField;
    EditText repeatField;
    EditText usernameField;
    NetworkService networkService;
    AuthApi authApi;
    UserCreate userCreateRequest;

    TextView debugText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registryButton = this.findViewById(R.id.registerButton);
        emailField = this.findViewById(R.id.emailRegisterField);
        passwordField = this.findViewById(R.id.passwordRegisterField);
        repeatField = this.findViewById(R.id.passwordRegisterRepeat);
        usernameField = this.findViewById(R.id.usernameRegisterField);
        debugText = this.findViewById(R.id.debugTextView);

        networkService = NetworkService.getInstance();
        authApi = networkService.getAuthApi();

        userCreateRequest = new UserCreate();

        registryButton.setOnClickListener(v -> {
            String password = passwordField.getText().toString();
            String repeated = repeatField.getText().toString();
            if (!password.equals(repeated)){
                int duration = Toast.LENGTH_SHORT;
                String message = getResources().getString(R.string.passwordsUnmatchMessage);
                Toast toast = Toast.makeText(this, message, duration);
                toast.show();
            }
            else {
                userCreateRequest.setEmail(emailField.getText().toString());
                userCreateRequest.setPassword(password);
                userCreateRequest.setUsername(usernameField.getText().toString());
                register();
            }
        });
    }

    protected void register(){
        authApi.register(userCreateRequest).enqueue(new Callback<UserCreate>() {
            @Override
            public void onResponse(
                    @NonNull Call<UserCreate> call,
                    @NonNull Response<UserCreate> response
            ) {
                if (response.isSuccessful()){
                    Intent intent = new Intent(
                            RegisterActivity.this,
                            VerifyActivity.class
                    );
                    assert response.body() != null;
                    intent.putExtra("email", response.body().getEmail());
                    startActivity(intent);
                    finish();
                }
                else if (response.errorBody() != null) {
                    try {
                        debugText.append(""+response.code());
                        debugText.append(response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(
                    @NonNull Call<UserCreate> call,
                    @NonNull Throwable t
            ) {
                debugText.setText("Error occurred while getting request!");
                t.printStackTrace();
            }
        });
    }

}
