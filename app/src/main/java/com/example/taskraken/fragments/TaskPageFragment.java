package com.example.taskraken.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.taskraken.R;
import com.example.taskraken.network.api.TasksApi;
import com.example.taskraken.network.schemas.tasks.TaskPage;
import com.example.taskraken.network.services.NetworkService;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskPageFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String id;

    private TaskPage taskPage;

    private TextView nameText;
    private TextView descText;
    private NetworkService networkService;

    private TasksApi tasksApi;
    private View rootView;

    public TaskPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TaskPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TaskPageFragment newInstance(String param1, String param2) {
        TaskPageFragment fragment = new TaskPageFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView =  inflater.inflate(R.layout.fragment_task_page, container, false);
        tasksApi.taskPage(id).enqueue(new Callback<TaskPage>() {
            @Override
            public void onResponse(Call<TaskPage> call, Response<TaskPage> response) {
                if (response.body() != null){
                    taskPage = response.body();
                    nameText = rootView.findViewById(R.id.textView_task_name);
                    nameText.setText(taskPage.getName());
                    descText = rootView.findViewById(R.id.textView_text_desc_FTsP);
                    descText.setText(taskPage.getDescription());
                }
            }

            @Override
            public void onFailure(Call<TaskPage> call, Throwable throwable) {

            }
        });


        return rootView;
    }
}