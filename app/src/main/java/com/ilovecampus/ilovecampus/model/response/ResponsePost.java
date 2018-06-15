package com.ilovecampus.ilovecampus.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponsePost {

    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("data")
    @Expose
    String data;

    public ResponsePost(String status, String data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public String getData() {
        return data;
    }

}
