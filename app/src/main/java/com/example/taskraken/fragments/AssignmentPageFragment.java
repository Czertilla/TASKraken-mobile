package com.example.taskraken.fragments;

import android.content.Context;
import android.os.Bundle;

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
import com.example.taskraken.network.api.TasksApi;
import com.example.taskraken.network.schemas.tasks.TaskPage;
import com.example.taskraken.network.services.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssignmentPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View rootView;
    private Context context;
    private EditText nameEditText;
    private EditText descEditText;
    private NetworkService networkService;
    private TasksApi tasksApi;


    private TaskPage taskPage;
    private String id;
    private NavController navController;

    public AssignmentPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AssignmentPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AssignmentPageFragment newInstance(String param1, String param2) {
        AssignmentPageFragment fragment = new AssignmentPageFragment();
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
            id = getArguments().getString("id");
            networkService = NetworkService.getInstance();
            tasksApi = networkService.getTaskApi();


        }
    }

    private void getPage(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_assignment_page, container, false);
        context = rootView.getContext();

        tasksApi.taskPage(id).enqueue(new Callback<TaskPage>() {
            @Override
            public void onResponse(Call<TaskPage> call, Response<TaskPage> response) {
                if (response.body() != null){
                    taskPage = response.body();
                    nameEditText = rootView.findViewById(R.id.editText_task_name_FAsP);
                    descEditText = rootView.findViewById(R.id.editText_name_desc_FAsP);
                    setUpNavController();
                    setUpButton();
                }
            }

            @Override
            public void onFailure(Call<TaskPage> call, Throwable throwable) {

            }
        });



        return rootView;
    }
    private void setUpNavController(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
    }


    private void setUpButton() {
        Button button = rootView.findViewById(R.id.button_EDIT_TASK);
        button.setOnClickListener(v -> {
            navController.navigate(R.id.action_assignmentPageFragment_to_fragment_assignments);
        });
    }
}