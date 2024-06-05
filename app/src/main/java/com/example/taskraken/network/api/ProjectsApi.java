package com.example.taskraken.network.api;

import com.example.taskraken.network.schemas.projects.AddProjectResponse;
import com.example.taskraken.network.schemas.projects.AddTaskRequest;
import com.example.taskraken.network.schemas.structs.RegistOrgResponse;
import com.example.taskraken.network.schemas.tasks.CheckList;

import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ProjectsApi {
    String prefix = "/project";

    @GET(prefix+"/{project_id}/select")
    Call<Object> selectProject(
            @Path("project_id")UUID project_id
            );

    @POST(prefix+"/new")
    Call<AddProjectResponse> createProject(
            @Query("name") String name,
            @Query("desctription") String description
    );

    @POST(prefix+"/add-task")
    Call<Object> addTask(
            @Query("name") String name,
            @Query("desctription") String description,
            @Body AddTaskRequest addTaskRequest
    );
}
