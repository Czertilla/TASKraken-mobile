package com.example.taskraken.network.schemas.projects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.UUID;

public class AddProjectResponse {
    @SerializedName("id")
    @Expose
    private UUID id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("deadline")
    @Expose
    private DateFormat deadline;

    @SerializedName("organization_id")
    @Expose
    private UUID organizationId;

    @SerializedName("creator_id")
    @Expose
    private UUID creatorId;

    @SerializedName("status")
    @Expose
    private String status;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DateFormat getDeadline() {
        return deadline;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public UUID getCreatorId() {
        return creatorId;
    }

    public String getStatus() {
        return status;
    }
}
