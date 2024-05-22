package com.example.taskraken.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskraken.R;
import com.example.taskraken.network.schemas.tasks.TaskPreview;

import java.util.List;

public class TaskRecyclerAdapter extends RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder> {

    Context context;
    List<TaskPreview> data;

    public TaskRecyclerAdapter(List<TaskPreview> data){
        this.data = data;
    }

    @NonNull
    @Override
    public TaskRecyclerAdapter.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType
    ) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view  = layoutInflater.inflate(R.layout.card_view_task,parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaskRecyclerAdapter.ViewHolder holder, int position) {
        TaskPreview preview = data.get(position);
        holder.taskName.setText(preview.getName());
        holder.taskDescription.setText(preview.getDescription());
        holder.id.setText(preview.getId().toString());
        holder.timestamps.setText(
                preview.getCreated_at().toString() + "---" +
                        preview.getEdited_at().toString()
        );

        holder.cardView.setOnClickListener(v -> {
//            TODO implement
            Toast.makeText(context, "Task page in development", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName;
        TextView taskDescription;
        TextView timestamps;
        TextView id;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.taskCard);
            taskName = itemView.findViewById(R.id.taskName);
            id = itemView.findViewById(R.id.text_view_task_id_CT);
            taskDescription = itemView.findViewById(R.id.text_view_task_desc);
            timestamps = itemView.findViewById(R.id.text_view_task_timestamps);
        }
    }
}
