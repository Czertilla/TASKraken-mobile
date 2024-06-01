package com.example.taskraken.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taskraken.R;
import com.example.taskraken.adapters.TaskRecyclerAdapter;
import com.example.taskraken.network.api.TasksApi;
import com.example.taskraken.network.schemas.pagination.Pagination;
import com.example.taskraken.network.schemas.tasks.TaskPreview;
import com.example.taskraken.network.schemas.tasks.TaskPreviewPagination;
import com.example.taskraken.network.services.NetworkService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AssignmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AssignmentFragment extends Fragment {
    Context context;
    View rootView;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView tasksRecyclerView;
    TaskRecyclerAdapter recyclerAdapter;
    List<TaskPreview> tasksList;

    NetworkService networkService;
    TasksApi tasksApi;

    TextView textView;

    int pageSize;

    int total;

    Animation rotateOpen;

    Animation rotateClose;
    NavController navController;
    Animation fromButton;
    Animation toButton;
    FloatingActionButton
            addFloatButton,
            addOrgButton,
            addRoleButton,
            addProjectButton,
            addTaskButton
        ;

    List<FloatingActionButton> subButtons;

    boolean clicked = true;

    public AssignmentFragment() {
        networkService = NetworkService.getInstance();
        tasksApi = networkService.getTaskApi();
        pageSize = 10;
        tasksList = new ArrayList<>();
        subButtons = new ArrayList<>();
    }


    public static AssignmentFragment newInstance() {
        return new AssignmentFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_assignments, container, false);
        context = rootView.getContext();

        setUpNavController();
        setUpFloatingButtons();
        setUpAnimations();
        setUpInterface();

        setUpSwipe();

        tasksRecyclerView = rootView.findViewById(R.id.recycler_view_assignments);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        tasksRecyclerView.setAdapter(recyclerAdapter = new TaskRecyclerAdapter(tasksList, navController, true));
        setTasksResponse();

        return rootView;
    }

    private void setUpInterface() {
        textView = rootView.findViewById(R.id.text_no_assignments_message);
        addFloatButton.setOnClickListener(v -> {
            onAddFloatButtonClicked();
        });
        addOrgButton.setOnClickListener(v -> {
            onAddFloatButtonClicked();
            navController.navigate(R.id.navigateToOrgBlankFragment);
        });
        addProjectButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_fragment_assignments_to_addProjectFragment);
        });
        addRoleButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_fragment_assignments_to_createSubordinateFragment);
            Toast.makeText(
                    context,
                    "add Roles in development",
                    Toast.LENGTH_SHORT
            ).show();
        });
        addTaskButton.setOnClickListener(v -> {
            navController.navigate(R.id.action_fragment_assignments_to_addTaskFragment);
        });
    }

    private void setUpAnimations() {
        toButton = AnimationUtils.loadAnimation(context, R.anim.to_button_anim);
        fromButton = AnimationUtils.loadAnimation(context, R.anim.from_button_anim);
        rotateOpen = AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim);
    }

    private void setUpFloatingButtons() {
        addFloatButton = rootView.findViewById(R.id.floating_button_add_FAs);
        subButtons.add(addOrgButton = rootView.findViewById(R.id.button_regist_org_nav));
        subButtons.add(addProjectButton = rootView.findViewById(R.id.button_create_project));
        subButtons.add(addRoleButton = rootView.findViewById(R.id.button_create_subordinate_nav));
        subButtons.add(addTaskButton = rootView.findViewById(R.id.button_create_new_task));
    }

    private void setUpNavController(){
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager
                .findFragmentById(R.id.fragment_container);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
    }

    private void onAddFloatButtonClicked(){
        subButtons.forEach(b -> {
            b.setAnimation(clicked ? fromButton : toButton);
            b.setVisibility(clicked ? View.VISIBLE: View.INVISIBLE);
            b.setClickable(clicked);
        });
        addFloatButton.setAnimation(clicked ? rotateOpen : rotateClose);
        clicked = !clicked;
    }

    private void setUpSwipe() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_assignments);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            setTasksResponse();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void setTasksResponse(){
        setTasksResponse(0, pageSize);
    }

    private void setTasksResponse(int page){
        setTasksResponse(page, pageSize);
    }

    private void setTasksResponse(int page, int size){
        tasksApi.myAssignments(page, size).enqueue(new Callback<TaskPreviewPagination>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(
                    @NonNull Call<TaskPreviewPagination> call,
                    @NonNull Response<TaskPreviewPagination> response
            ) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    Pagination pagination = response.body().getPagination();
                    total = pagination.getTotal();
                    List<TaskPreview> previews = response.body().getResult();
                    int begin = pagination.getPage() * pagination.getSize();
                    int end = (pagination.getPage() + 1) * pagination.getSize();
                    if (begin == 0 && end > begin && previews.isEmpty()){
                        tasksList.clear();
                    }
                    else{
                        for (int i=begin, j=0; i < end &&  j < previews.size(); i++, j++){
                            if (i >= tasksList.size())
                                tasksList.add(previews.get(j));
                            else
                                tasksList.set(i, previews.get(j));
                        }
                    }
                }else{
//                    TODO implement
//                    TextView debugText = requireActivity().findViewById(R.id.debugTextView);
//                    assert response.errorBody() != null;
//                    debugText.setText(response.code()+ response.errorBody().toString());
                }
                textView.setVisibility(tasksList.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(
                    @NonNull Call<TaskPreviewPagination> call,
                    @NonNull Throwable throwable
            ) {

            }
        });
    }

}