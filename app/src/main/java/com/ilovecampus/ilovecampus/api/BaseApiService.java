package com.ilovecampus.ilovecampus.api;

import com.ilovecampus.ilovecampus.model.response.ResponseExtras;
import com.ilovecampus.ilovecampus.model.response.ResponseLokasi;
import com.ilovecampus.ilovecampus.model.response.ResponseMember;
import com.ilovecampus.ilovecampus.model.response.ResponsePost;
import com.ilovecampus.ilovecampus.model.response.ResponseRuang;
import com.ilovecampus.ilovecampus.model.response.ResponseUser;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {

    @FormUrlEncoded
    @POST("login/")
    Call<ResponsePost> login(@Field("id_member") String idMember, @Field("password") String password);

    @FormUrlEncoded
    @POST("register/")
    Call<ResponsePost> register(@Field("id_member") String idMember,
                                @Field("nama_member") String namaMember,
                                @Field("detail_ruang") String detailRuang,
                                @Field("id_ruang") String idRuang,
                                @Field("no_hp") String noHp,
                                @Field("email") String email,
                                @Field("alamat") String alamat,
                                @Field("password") String password,
                                @Field("status") String status);

    @GET("profile/{id_member}")
    Call<ResponseUser> getUserData(@Path("id_member") String idMember);

    @FormUrlEncoded
    @POST("profile/edit/")
    Call<ResponsePost> editProfile(@Field("id_member") String idMember,
                                @Field("nama_member") String namaMember,
                                @Field("detail_ruang") String detailRuang,
                                @Field("id_ruang") String idRuang,
                                @Field("no_hp") String noHp,
                                @Field("email") String email,
                                @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("profile/password/")
    Call<ResponsePost> editPassword(@Field("id_member") String idMember,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("sms/json")
    Call<ResponsePost> sendPassword(@Query("api_key") String apiKey, @Query("api_secret") String apiSecret,
                                    @Field("from") String from, @Field("to") String to, @Field("text") String text);

    @FormUrlEncoded
    @POST("lokasi/awal")
    Call<ResponsePost> tambahLokasiAwal(@Field("id_member") String idMember,
                                   @Field("latitude") String latitude,
                                   @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("lokasi/update")
    Call<ResponsePost> updateLokasi(@Field("id_member") String idMember,
                                        @Field("latitude") String latitude,
                                        @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("lokasi/clear")
    Call<ResponsePost> hapusLokasi(@Field("id_member") String idMember);

    @GET("lokasi/")
    Call<ResponseLokasi> getAllLokasi();

    @GET("member/{status}")
    Call<ResponseMember> getAllMember(@Path("status") String status);

    @GET("member/search/")
    Call<ResponseMember> searchMember(@Query("keyword") String keyword, @Query("status") String status);

    @GET("extras/")
    Call<ResponseExtras> getAllExtras();

    @GET("ruang/")
    Call<ResponseRuang> getAllRuang();

}