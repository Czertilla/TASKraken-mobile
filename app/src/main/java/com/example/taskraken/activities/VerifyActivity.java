package com.example.taskraken.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.taskraken.R;
import com.example.taskraken.network.api.AuthApi;
import com.example.taskraken.network.schemas.auth.VerifyRequest;
import com.example.taskraken.network.schemas.auth.VerifyTokenRequest;
import com.example.taskraken.network.services.NetworkService;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyActivity extends Activity {
    String email;
    NetworkService networkService;
    AuthApi authApi;
    Button sendButton;
    Button verifyButton;
    TextView coolDownTimer;
    EditText emailField;
    EditText tokenField;
    VerifyTokenRequest verifyTokenRequest;
    VerifyRequest verifyRequest;
    TextView debugText;
    CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        Intent intent = this.getIntent();

        email = intent.getStringExtra("email");
        networkService = NetworkService.getInstance();
        authApi = networkService.getAuthApi();
        sendButton = this.findViewById(R.id.button_request_token);
        verifyButton = this.findViewById(R.id.button_verify);
        coolDownTimer = this.findViewById(R.id.text_view_cool_down_AV);
        emailField = this.findViewById(R.id.field_email_AV);
        tokenField = this.findViewById(R.id.field_verify_token);
        debugText = this.findViewById(R.id.text_view_debug_AF);

        if (email != null) {
            emailField.setText(email);
        }

        verifyTokenRequest = new VerifyTokenRequest();
        verifyRequest = new VerifyRequest();

        timer = new CountDownTimer(60000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                int min = (int) millisUntilFinished / 60000;
                int sec = (int) millisUntilFinished / 1000 % 60;
                coolDownTimer.setText(min+":"+sec);
            }

            @Override
            public void onFinish() {
                coolDownTimer.setText("");
                sendButton.setEnabled(true);
            }
        };

        sendButton.setOnClickListener(v -> {
            sendButton.setEnabled(false);
            timer.start();

            verifyTokenRequest.setEmail(emailField.getText().toString());
            authApi.requestToken(verifyTokenRequest)
                .enqueue(new retrofit2.Callback<Object>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(
                            @NonNull Call<Object> call,
                            @NonNull Response<Object> response
                    ) {
                        debugText.setText(""+response.code());
                    }

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onFailure(
                            @NonNull Call<Object> call,
                            @NonNull Throwable t
                    ) {
                        debugText.setText("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
        });

        verifyButton.setOnClickListener(v -> {
            verifyRequest.setToken(tokenField.getText().toString());
            authApi.verify(verifyRequest)
                    .enqueue(new Callback<Object>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onResponse(
                                @NonNull Call<Object> call,
                                @NonNull Response<Object> response
                        ) {
                            if (response.isSuccessful()){
                                VerifyActivity.this.finish();
                            }
                            else  if (response.errorBody() != null){
                                try {
                                    debugText.setText(""+response.code());
                                    debugText.append(response.errorBody().string());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onFailure(
                                @NonNull Call<Object> call,
                                @NonNull Throwable t
                        ) {
                            debugText.setText("Error occurred while getting request!");
                            t.printStackTrace();
                        }
                    });
        });
    }
}
