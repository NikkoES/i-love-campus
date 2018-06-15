package com.ilovecampus.ilovecampus.model.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Member implements Serializable {

    @SerializedName("id_member")
    String idMember;
    @SerializedName("nama_member")
    String namaMember;
    @SerializedName("detail_ruang")
    String detailRuang;
    @SerializedName("id_ruang")
    String idRuang;
    @SerializedName("no_hp")
    String noHp;
    @SerializedName("email")
    String email;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("password")
    String password;
    @SerializedName("status")
    String status;
    @SerializedName("tipe_ruang")
    String tipeRuang;

    public Member(String idMember, String namaMember, String detailRuang, String idRuang, String noHp, String email, String alamat, String password, String status, String tipeRuang) {
        this.idMember = idMember;
        this.namaMember = namaMember;
        this.detailRuang = detailRuang;
        this.idRuang = idRuang;
        this.noHp = noHp;
        this.email = email;
        this.alamat = alamat;
        this.password = password;
        this.status = status;
        this.tipeRuang = tipeRuang;
    }

    public String getIdMember() {
        return idMember;
    }

    public String getNamaMember() {
        return namaMember;
    }

    public String getDetailRuang() {
        return detailRuang;
    }

    public String getIdRuang() {
        return idRuang;
    }

    public String getNoHp() {
        return noHp;
    }

    public String getEmail() {
        return email;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getPassword() {
        return password;
    }

    public String getStatus() {
        return status;
    }

    public String getTipeRuang() {
        return tipeRuang;
    }

}
