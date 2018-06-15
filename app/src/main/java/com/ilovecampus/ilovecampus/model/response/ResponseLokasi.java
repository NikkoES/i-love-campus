package com.ilovecampus.ilovecampus.model.response;

import com.google.gson.annotations.SerializedName;
import com.ilovecampus.ilovecampus.model.data.Lokasi;

import java.util.List;

public class ResponseLokasi {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<Lokasi> listLokasi;

    public ResponseLokasi(String status, List<Lokasi> listLokasi) {
        this.status = status;
        this.listLokasi = listLokasi;
    }

    public String getStatus() {
        return status;
    }

    public List<Lokasi> getListLokasi() {
        return listLokasi;
    }
}
