package com.example.taskraken.network.api;

import com.example.taskraken.network.schemas.tasks.TaskPreviewPagination;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TasksApi {
    String prefix = "/tasks";

    @GET(prefix+"/my")
    Call<TaskPreviewPagination> myTasks(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET(prefix+"/my-assignments")
    Call<TaskPreviewPagination> myAssignments(
            @Query("page") int page,
            @Query("size") int size
    );
}
