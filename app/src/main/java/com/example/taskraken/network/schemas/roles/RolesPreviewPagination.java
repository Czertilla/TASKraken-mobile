package com.example.taskraken.network.schemas.roles;

import com.example.taskraken.network.schemas.pagination.Pagination;
import com.example.taskraken.network.schemas.roles.RolePreview;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RolesPreviewPagination {
    @SerializedName("result")
    @Expose
    List<RolePreview> result;

    @SerializedName("pagination")
    @Expose
    Pagination pagination;

    public List<RolePreview> getResult() {
        return result;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
