package com.example.taskraken.network.schemas.tasks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

public class TaskPreview {
    @SerializedName("id")
    @Expose
    private UUID id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("organization_id")
    @Expose
    private UUID organization_id;

    @SerializedName("description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    @SerializedName("created_at")
    @Expose
    private Date created_at;

    @SerializedName("edited_at")
    @Expose
    private Date edited_at;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID getOrganization_id() {
        return organization_id;
    }


    public Date getCreated_at() {
        return created_at;
    }

    public Date getEdited_at() {
        return edited_at;
    }
}
