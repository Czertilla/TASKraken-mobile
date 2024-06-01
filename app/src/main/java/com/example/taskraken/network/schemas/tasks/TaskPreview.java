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
    private UUID organizationId;

    @SerializedName("description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    @SerializedName("created_at")
    @Expose
    private Date createdAt;

    @SerializedName("edited_at")
    @Expose
    private Date editedAt;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getEditedAt() {
        return editedAt;
    }
}
