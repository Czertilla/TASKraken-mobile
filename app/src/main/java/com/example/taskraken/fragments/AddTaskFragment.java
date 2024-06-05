package com.example.taskraken.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.taskraken.R;
import com.example.taskraken.network.api.ProjectsApi;
import com.example.taskraken.network.api.RolesApi;
import com.example.taskraken.network.schemas.projects.AddProjectResponse;
import com.example.taskraken.network.schemas.projects.AddTaskRequest;
import com.example.taskraken.network.schemas.roles.RolePreview;
import com.example.taskraken.network.schemas.roles.RolesPreviewPagination;
import com.example.taskraken.network.services.NetworkService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;
    private Context context;

    protected List<RolePreview> roles;

    private Spinner resposibleSpinner;
    private Button createButton;
    private NetworkService networkService;
    private ProjectsApi projectApi;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private NavController navController;
    private RolesApi roleApi;
    private UUID RespUUID;

    public AddTaskFragment() {
        roles = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        context = rootView.getContext();

        setUpNavController();
        setUpNetwork();
        getRoles();

        return rootView;
    }

    private void getRoles(){
        roleApi.all().enqueue(new Callback<RolesPreviewPagination>() {
            @Override
            public void onResponse(
                    @NonNull Call<RolesPreviewPagination> call,
                    @NonNull Response<RolesPreviewPagination> response
            ) {
                if (response.isSuccessful() & response.body() != null){
                    roles = response.body().getResult();
                    setUpSpinner();
                    setUpButton();
                }
                else {
                    Toast.makeText(rootView.getContext(), "Roles not given", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(
                    @NonNull Call<RolesPreviewPagination> call,
                    @NonNull Throwable throwable
            ) {
                Toast.makeText(rootView.getContext(), "Bad Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUpSpinner() {

        resposibleSpinner = rootView.findViewById(R.id.spinner_add_responsible);
        List<String> idList = new ArrayList<>();
        for (RolePreview role: roles)
            idList.add(role.getName());
        ArrayAdapter roleTemplateAdapter = new ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                idList
        );
        roleTemplateAdapter.setDropDownViewResource(
                android.R.layout.simple_dropdown_item_1line
        );
        resposibleSpinner.setAdapter(roleTemplateAdapter);

        resposibleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                RespUUID = roles.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpNetwork() {
        networkService = NetworkService.getInstance();
        projectApi = networkService.getProjectApi();
        roleApi = networkService.getRoleApi();
    }

    private void setUpButton() {
        nameEditText = rootView.findViewById(R.id.editText_task_name);
        descriptionEditText = rootView.findViewById(R.id.editText_task_desc);
        createButton = rootView.findViewById(R.id.button_create_task_FaT);
        createButton.setOnClickListener(v -> {
            AddTaskRequest addTaskRequest = new AddTaskRequest();
            addTaskRequest.setResponsibilities(Arrays.asList(new UUID[]{RespUUID}));
            projectApi.addTask(
                    nameEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    addTaskRequest
            ).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(
                        @NonNull Call<Object> call,
                        @NonNull Response<Object> response
                ) {
                    if (response.isSuccessful()){
                        navController.navigate(R.id.action_addTaskFragment_to_fragment_assignments);
                    }
                    else if (response.errorBody() != null){
                        String a;
                        try {
                            a = response.errorBody().string();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Toast.makeText(rootView.getContext(), a, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(
                        @NonNull Call<Object> call,
                        @NonNull Throwable throwable
                ) {

                }
            });
        });
    }
    private void setUpNavController(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
    }
}