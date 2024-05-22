package com.example.taskraken.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.taskraken.R;
import com.example.taskraken.adapters.TaskRecyclerAdapter;
import com.example.taskraken.db.repository.CookieRepository;
import com.example.taskraken.db.repository.UserRepository;
import com.example.taskraken.network.services.NetworkService;
import com.example.taskraken.network.api.UsersApi;
import com.example.taskraken.network.schemas.users.UserRead;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

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
    ProgressBar progressBar;
    ImageView sideMenuButton;
    NavigationView navigationView;
    DrawerLayout mainDrawerLayout;
    RecyclerView taskRecyclerView;
    TaskRecyclerAdapter taskRecyclerAdapter;
    BottomNavigationView bottomNavigationView;
    NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpDatabase();
        setContentView(R.layout.activity_main);

        mainDrawerLayout = findViewById(R.id.mainDrawerLayout);
        progressBar = findViewById(R.id.progressBar);

        setUpNetwork();
        setUpSideMenu();
        setUpBottomMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuth();
        progressBar.setVisibility(ProgressBar.GONE);
    }

    private void setUpNetwork(){
        networkService = NetworkService.getInstance();
        usersApi = networkService.getUserApi();
        debugTextView = this.findViewById(R.id.debugTextView);
    }

    private void setUpDatabase(){
        Context context = this.getApplicationContext();
        CookieRepository.setInstance(context);
        userRepository = new UserRepository(context);
    }

    private void setUpSideMenu(){
        sideMenuButton = findViewById(R.id.sideMenuButton);
        sideMenuButton.setOnClickListener(v -> {
            mainDrawerLayout.openDrawer(GravityCompat.START);
        });
        navigationView = findViewById(R.id.mainNavigationView);
        navigationView.setNavigationItemSelectedListener(new MainNavigationMenuListener());
    }

    private void setUpBottomMenu(){
        navController = (
                (NavHostFragment) Objects
                        .requireNonNull(
                                getSupportFragmentManager()
                                        .findFragmentById(R.id.fragment_container)
                        )
        ).getNavController();
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.tasksNav) replaceFragment(new TasksFragment());
            if (id == R.id.organizationNav) replaceFragment(new OrganizationFragment());
            return true;
        });
        replaceFragment(new TasksFragment());
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
                    userRepository.insert(user);
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

    class MainNavigationMenuListener implements NavigationView.OnNavigationItemSelectedListener {
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.logOutItem){
                networkService.getAuthApi().logout().enqueue(new Callback<Object>() {

                    @Override
                    public void onResponse(
                            @NonNull Call<Object> call,
                            @NonNull Response<Object> response) {
                        if (response.isSuccessful())
                            MainActivity.this.checkAuth();
                    }

                    @Override
                    public void onFailure(
                            @NonNull Call<Object> call,
                            @NonNull Throwable t) {
                        debugTextView.append("\n"+"something gone wrong with server connection");
                    }
                });
                return true;
            }
            return false;
        }
    }

//    private void getUserFromCache(){
//        User user = userRepository.getPickedUser();
//        boolean flag;
//        if (user == null){
//            flag = false;
//            return;
//        }
//        networkService.getAuthApi().login(
//                user.email,
//                user.password
//        ).enqueue(new Callback<UserRead>() {
//            @Override
//            public void onResponse(
//                    @NonNull Call<UserRead> call,
//                    @NonNull Response<UserRead> response
//            ) {
//                flag = response.isSuccessful();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<UserRead> call, @NonNull Throwable t) {
//                Log.e("", "");
//                flag = false;
//            }
//        });
//    }
}