package com.example.taskraken.network.schemas.tasks;

import com.example.taskraken.network.schemas.pagination.Pagination;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskPreviewPagination {
    @SerializedName("result")
    @Expose
    List<TaskPreview> result;

    @SerializedName("pagination")
    @Expose
    Pagination pagination;

    public List<TaskPreview> getResult() {
        return result;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
