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
import android.widget.Button;
import android.widget.EditText;

import com.example.taskraken.R;
import com.example.taskraken.network.api.ProjectsApi;
import com.example.taskraken.network.schemas.projects.AddProjectResponse;
import com.example.taskraken.network.services.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProjectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;
    private Context context;
    private Button createButton;
    private NetworkService networkService;
    private ProjectsApi projectApi;
    private EditText nameEditText;
    private EditText descriptionEditText;
    private NavController navController;

    public AddProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProjectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProjectFragment newInstance(String param1, String param2) {
        AddProjectFragment fragment = new AddProjectFragment();
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
        rootView = inflater.inflate(R.layout.fragment_add_project, container, false);
        context = rootView.getContext();

        setUpNavController();
        setUpNetwork();
        setUpButton();

        return rootView;
    }

    private void setUpNetwork() {
        networkService = NetworkService.getInstance();
        projectApi = networkService.getProjectApi();
    }

    private void setUpButton() {
        nameEditText = rootView.findViewById(R.id.editText_project_name);
        descriptionEditText = rootView.findViewById(R.id.editText_project_desc);
        createButton = rootView.findViewById(R.id.button_create_project_FaP);
        createButton.setOnClickListener(v -> {
            projectApi.createProject(
                    nameEditText.getText().toString(),
                    descriptionEditText.getText().toString()
            ).enqueue(new Callback<AddProjectResponse>() {
                @Override
                public void onResponse(
                        @NonNull Call<AddProjectResponse> call,
                        @NonNull Response<AddProjectResponse> response
                ) {
                    if (response.isSuccessful()){
                        navController.navigate(R.id.action_addProjectFragment_to_fragment_assignments);
                    }
                }

                @Override
                public void onFailure(
                        @NonNull Call<AddProjectResponse> call,
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