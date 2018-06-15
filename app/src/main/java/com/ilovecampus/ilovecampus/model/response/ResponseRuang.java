package com.ilovecampus.ilovecampus.model.response;

import com.google.gson.annotations.SerializedName;
import com.ilovecampus.ilovecampus.model.data.Ruang;

import java.util.List;

public class ResponseRuang {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<Ruang> listRuang;

    public ResponseRuang(String status, List<Ruang> listRuang) {
        this.status = status;
        this.listRuang = listRuang;
    }

    public String getStatus() {
        return status;
    }

    public List<Ruang> getListRuang() {
        return listRuang;
    }
}
