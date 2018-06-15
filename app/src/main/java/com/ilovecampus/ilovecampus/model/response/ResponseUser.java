package com.ilovecampus.ilovecampus.model.response;

import com.google.gson.annotations.SerializedName;
import com.ilovecampus.ilovecampus.model.data.Member;

public class ResponseUser {

    @SerializedName("status")
    String status;
    @SerializedName("data")
    Member user;

    public ResponseUser(String status, Member user) {
        this.status = status;
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public Member getUser() {
        return user;
    }

}
