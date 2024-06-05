package com.example.taskraken.network.schemas.structs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class RegistOrgResponse {
    @SerializedName("gen_dir_id")
    @Expose
    private UUID genDirId;

    @SerializedName("org_id")
    @Expose
    private UUID orgId;

    public UUID getGenDirId() {
        return genDirId;
    }

    public UUID getOrgId() {
        return orgId;
    }
}
