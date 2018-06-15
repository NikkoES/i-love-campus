package com.ilovecampus.ilovecampus.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ruang implements Serializable {

    @SerializedName("id_ruang")
    String idRuang;
    @SerializedName("tipe_ruang")
    String tipeRuang;

    public Ruang(String idRuang, String tipeRuang) {
        this.idRuang = idRuang;
        this.tipeRuang = tipeRuang;
    }

    public String getIdRuang() {
        return idRuang;
    }

    public String getTipeRuang() {
        return tipeRuang;
    }
}
