package com.example.taskraken.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskraken.R;
import com.example.taskraken.activities.MainActivity;
import com.example.taskraken.fragments.RolesFragment;
import com.example.taskraken.fragments.TasksFragment;
import com.example.taskraken.network.api.RolesApi;
import com.example.taskraken.network.api.UsersApi;
import com.example.taskraken.network.schemas.roles.RolePreview;
import com.example.taskraken.network.services.NetworkService;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoleRecyclerAdapter extends RecyclerView.Adapter<RoleRecyclerAdapter.ViewHolder> {

    Context context;
    List<RolePreview> data;

    NetworkService networkService;

    Fragment fragment;

    RolesApi rolesApi;
    OnBackPressedDispatcher backPress;

    public RoleRecyclerAdapter(List<RolePreview> data, OnBackPressedDispatcher back){
        this.data = data;
        networkService = NetworkService.getInstance();
        rolesApi = networkService.getRoleApi();
        backPress = back;
    }

    @NonNull
    @Override
    public RoleRecyclerAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view  = layoutInflater.inflate(R.layout.card_view_role,parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RoleRecyclerAdapter.ViewHolder holder, int position) {
        RolePreview preview = data.get(position);
        holder.roleName.setText(preview.getName());
        holder.roleOrgName.setText(preview.getOrganizationName());
        UUID id = preview.getId();
        holder.id.setText(id.toString());
        holder.level.setText(String.valueOf(preview.getLevel()));

        holder.cardView.setOnClickListener(v -> {
            rolesApi.selectRole(id).enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(v.getContext(), "Something goes wrong", Toast.LENGTH_SHORT)
                                .show();
                    }
                    else if (response.body() != null){
                        Toast.makeText(v.getContext(), "selected", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable throwable) {
                    Toast.makeText(v.getContext(), "bad connection", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            try {
                backPress.onBackPressed();
            }
            catch (Exception e){
                Toast.makeText(v.getContext(), "Something goes wrong", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView roleName;
        TextView roleOrgName;
        TextView level;
        TextView id;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.roleCard);
            roleName = itemView.findViewById(R.id.roleName);
            id = itemView.findViewById(R.id.text_view_role_id_CR);
            roleOrgName = itemView.findViewById(R.id.text_view_role_org_name);
            level = itemView.findViewById(R.id.text_view_role_level);
        }
    }
}
