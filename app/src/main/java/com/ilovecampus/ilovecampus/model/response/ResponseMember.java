package com.ilovecampus.ilovecampus.model.response;

import com.google.gson.annotations.SerializedName;
import com.ilovecampus.ilovecampus.model.data.Member;

import java.util.List;

public class ResponseMember {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private List<Member> listMember;

    public ResponseMember(String status, List<Member> listMember) {
        this.status = status;
        this.listMember = listMember;
    }

    public String getStatus() {
        return status;
    }

    public List<Member> getListMember() {
        return listMember;
    }
}
