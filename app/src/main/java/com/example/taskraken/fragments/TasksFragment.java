package com.example.taskraken.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.taskraken.R;
import com.example.taskraken.adapters.TaskRecyclerAdapter;
import com.example.taskraken.network.api.TasksApi;
import com.example.taskraken.network.schemas.pagination.Pagination;
import com.example.taskraken.network.schemas.tasks.TaskPreview;
import com.example.taskraken.network.schemas.tasks.TaskPreviewPagination;
import com.example.taskraken.network.services.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TasksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TasksFragment extends Fragment {

    View rootView;
    Context context;
    RecyclerView tasksRecyclerView;
    TaskRecyclerAdapter recyclerAdapter;
    List<TaskPreview> tasksList;

    NetworkService networkService;
    TasksApi tasksApi;

    TextView textView;

    int pageSize;

    int total;

    public TasksFragment() {

        networkService = NetworkService.getInstance();
        tasksApi = networkService.getTaskApi();
        pageSize = 10;
        tasksList = new ArrayList<>();
    }


    public static TasksFragment newInstance(NavController navController) {
        TasksFragment fragment = new TasksFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
        context = rootView.getContext();

        textView = rootView.findViewById(R.id.tasksFragmentEmptyText);

        tasksRecyclerView = rootView.findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        tasksRecyclerView.setAdapter(recyclerAdapter = new TaskRecyclerAdapter(tasksList));
        setTasksResponse();
        return rootView;
    }

    private void setTasksResponse(){
        setTasksResponse(0, pageSize);
    }

    private void setTasksResponse(int page){
        setTasksResponse(page, pageSize);
    }

    private void setTasksResponse(int page, int size){
        tasksApi.myTasks(page, size).enqueue(new Callback<TaskPreviewPagination>() {
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