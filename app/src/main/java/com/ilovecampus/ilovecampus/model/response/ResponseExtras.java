package com.ilovecampus.ilovecampus.model.response;

import com.google.gson.annotations.SerializedName;
import com.ilovecampus.ilovecampus.model.data.Extras;

public class ResponseExtras {

    @SerializedName("status")
    String status;
    @SerializedName("data")
    Extras extras;

    public ResponseExtras(String status, Extras extras) {
        this.status = status;
        this.extras = extras;
    }

    public String getStatus() {
        return status;
    }

    public Extras getExtras() {
        return extras;
    }

}
