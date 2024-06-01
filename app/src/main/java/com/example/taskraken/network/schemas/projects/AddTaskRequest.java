package com.example.taskraken.network.schemas.projects;

import com.example.taskraken.network.schemas.tasks.CheckList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddTaskRequest {
    @SerializedName("checklists")
    @Expose
    private List<CheckList> checkLists;

    public AddTaskRequest() {
        responsibilities = new ArrayList<>();
        checkLists = new ArrayList<>();
    }

    @SerializedName("responsobilities")
    @Expose
    private List<UUID> responsibilities;

    public void setCheckLists(List<CheckList> checkLists) {
        this.checkLists = checkLists;
    }

    public void setResponsibilities(List<UUID> responsibilities) {
        this.responsibilities = responsibilities;
    }
}
