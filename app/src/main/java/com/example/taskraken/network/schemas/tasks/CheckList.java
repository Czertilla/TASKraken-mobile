package com.example.taskraken.network.schemas.tasks;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CheckList {
    public static class CheckPoint {
        @Nullable
        @SerializedName("id")
        @Expose
        private UUID id;

        @SerializedName("name")
        @Expose
        private String name;

        public CheckPoint(String name) {
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Nullable
        public UUID getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
    @Nullable
    @SerializedName("id")
    @Expose
    private UUID id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("checkpoints")
    @Expose
    private List<CheckPoint> checkpoints;
    public void setName(String name) {
        this.name = name;
    }

    public CheckList(String name, List<String> chkPntNames) {
        this.name = name;
        this.checkpoints = new ArrayList<>();
        for (String checkpoint: chkPntNames){
            checkpoints.add(new CheckPoint(checkpoint));
        }
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
