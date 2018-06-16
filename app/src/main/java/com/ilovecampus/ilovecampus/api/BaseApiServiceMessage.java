package com.ilovecampus.ilovecampus.api;

import com.ilovecampus.ilovecampus.model.response.ResponseExtras;
import com.ilovecampus.ilovecampus.model.response.ResponseLokasi;
import com.ilovecampus.ilovecampus.model.response.ResponseMember;
import com.ilovecampus.ilovecampus.model.response.ResponsePost;
import com.ilovecampus.ilovecampus.model.response.ResponseRuang;
import com.ilovecampus.ilovecampus.model.response.ResponseUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiServiceMessage {

    @FormUrlEncoded
    @POST("sms/json")
    Call<ResponsePost> sendPassword(@Query("api_key") String apiKey, @Query("api_secret") String apiSecret,
                                    @Field("from") String from, @Field("to") String to, @Field("text") String text);

}