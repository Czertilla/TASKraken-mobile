package com.example.taskraken.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedDispatcher;
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
import android.widget.TextView;

import com.example.taskraken.R;
import com.example.taskraken.adapters.RoleRecyclerAdapter;
import com.example.taskraken.network.api.RolesApi;
import com.example.taskraken.network.schemas.pagination.Pagination;
import com.example.taskraken.network.schemas.roles.RolePreview;
import com.example.taskraken.network.schemas.roles.RolesPreviewPagination;
import com.example.taskraken.network.services.NetworkService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RolesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RolesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    View rootView;

    SwipeRefreshLayout swipeRefreshLayout;
    Context context;
    RecyclerView rolesRecyclerView;
    RoleRecyclerAdapter recyclerAdapter;
    List<RolePreview> rolesList;

    NetworkService networkService;
    RolesApi rolesApi;

    TextView textView;

    int pageSize;

    int total;

    public RolesFragment() {
        networkService = NetworkService.getInstance();
        rolesApi = networkService.getRoleApi();
        pageSize = 10;
        rolesList = new ArrayList<>();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Role.
     */
    // TODO: Rename and change types and number of parameters
    public static RolesFragment newInstance(String param1, String param2) {
        RolesFragment fragment = new RolesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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
        rootView = inflater.inflate(R.layout.fragment_my_roles, container, false);
        context = rootView.getContext();

        textView = rootView.findViewById(R.id.text_view_no_roles);

        setUpSwipe();

        rolesRecyclerView = rootView.findViewById(R.id.recycler_view_roles);
        rolesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        OnBackPressedDispatcher back = new OnBackPressedDispatcher();
        rolesRecyclerView.setAdapter(recyclerAdapter = new RoleRecyclerAdapter(rolesList, back));
        setRolesResponse();
        return rootView;
    }

    private void setUpSwipe() {
        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh_roles);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            setRolesResponse();
            swipeRefreshLayout.setRefreshing(false);
        });
    }

    private void setRolesResponse(){
        setRolesResponse(0, pageSize);
    }

    private void setRolesResponse(int page){
        setRolesResponse(page, pageSize);
    }

    private void setRolesResponse(int page, int size){
        rolesApi.myRoles(page, size).enqueue(new Callback<RolesPreviewPagination>() {
            @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
            @Override
            public void onResponse(
                    @NonNull Call<RolesPreviewPagination> call,
                    @NonNull Response<RolesPreviewPagination> response
            ) {
                if (response.isSuccessful()){
                    assert response.body() != null;
                    Pagination pagination = response.body().getPagination();
                    total = pagination.getTotal();
                    List<RolePreview> previews = response.body().getResult();
                    int begin = pagination.getPage() * pagination.getSize();
                    int end = (pagination.getPage() + 1) * pagination.getSize();
                    if (begin == 0 && end > begin && previews.isEmpty()){
                        rolesList.clear();
                    }
                    else{
                        for (int i=begin, j=0; i < end &&  j < previews.size(); i++, j++){
                            if (i >= rolesList.size())
                                rolesList.add(previews.get(j));
                            else
                                rolesList.set(i, previews.get(j));
                        }
                    }
                }else{
//                    TODO implement
//                    TextView debugText = requireActivity().findViewById(R.id.debugTextView);
//                    assert response.errorBody() != null;
//                    debugText.setText(response.code()+ response.errorBody().toString());
                }
                textView.setVisibility(rolesList.isEmpty() ? View.VISIBLE : View.INVISIBLE);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(
                    @NonNull Call<RolesPreviewPagination> call,
                    @NonNull Throwable throwable
            ) {

            }
        });
    }
}
