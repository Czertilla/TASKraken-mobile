package com.example.taskraken.network.schemas.pagination;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {
    @SerializedName("page")
    @Expose
    int page;

    @SerializedName("size")
    @Expose
    int size;

    @SerializedName("total")
    int total;

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }
}
