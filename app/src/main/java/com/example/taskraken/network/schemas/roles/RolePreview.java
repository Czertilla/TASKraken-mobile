package com.example.taskraken.network.schemas.roles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class RolePreview {
    @SerializedName("id")
    @Expose
    private UUID id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("level")
    @Expose
    private int level;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    @SerializedName("organization_name")
    @Expose
    private String organizationName;

}
