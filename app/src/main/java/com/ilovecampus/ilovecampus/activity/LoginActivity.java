package com.ilovecampus.ilovecampus.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.api.BaseApiService;
import com.ilovecampus.ilovecampus.api.UtilsApi;
import com.ilovecampus.ilovecampus.model.data.Extras;
import com.ilovecampus.ilovecampus.model.data.Member;
import com.ilovecampus.ilovecampus.model.response.ResponseExtras;
import com.ilovecampus.ilovecampus.model.response.ResponsePost;
import com.ilovecampus.ilovecampus.model.response.ResponseUser;
import com.ilovecampus.ilovecampus.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_id_member)
    EditText etIdMember;
    @BindView(R.id.et_password)
    EditText etPassword;

    private SharedPreferencesUtils session, extras;

    JSONObject userProfile, dataExtras;

    private ProgressDialog loadingDaftar;

    String idMember, password;

    BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        apiService = UtilsApi.getAPIService();

        session = new SharedPreferencesUtils(this, "UserData");
        extras = new SharedPreferencesUtils(this, "ExtrasData");

        getExtras();

        if(session.checkIfDataExists("userProfile")){
            finish();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }

        loadingDaftar = new ProgressDialog(this);
        loadingDaftar.setTitle("Loading");
        loadingDaftar.setMessage("Checking Data");
        loadingDaftar.setCancelable(false);
    }

    @OnClick(R.id.btn_login)
    public void login(){
        loadingDaftar.show();
        if(TextUtils.isEmpty(etIdMember.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
            Toast.makeText(this, "Data belum lengkap !", Toast.LENGTH_SHORT).show();
            loadingDaftar.dismiss();
        }
        else{
            idMember = etIdMember.getText().toString();
            password = etPassword.getText().toString();
            checkLogin(idMember, password);
        }
    }

    private void checkLogin(String idMember, String password){
        final String sIdMember = idMember;
        apiService.login(sIdMember, password)
                .enqueue(new Callback<ResponsePost>() {
                    @Override
                    public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
                        if (response.body().getData().equalsIgnoreCase("1")){
                            loadingDaftar.dismiss();
                            getExtras();
                            getUserData(sIdMember);
                        }
                        else {
                            loadingDaftar.dismiss();
                            Toast.makeText(getApplicationContext(), "Gagal Masuk (Email dan Password tidak match) !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePost> call, Throwable t) {
                        loadingDaftar.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getUserData(String idMember){
        apiService.getUserData(idMember).enqueue(new Callback<ResponseUser>() {
            @Override
            public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                if (response.body().getStatus().equals("success")){
                    loadingDaftar.dismiss();
                    Member data = response.body().getUser();

                    userProfile = new JSONObject();
                    try {
                        userProfile.put("id_member", data.getIdMember());
                        userProfile.put("nama_member", data.getNamaMember());
                        userProfile.put("detail_ruang", data.getDetailRuang());
                        userProfile.put("id_ruang", data.getIdRuang());
                        userProfile.put("no_hp", data.getNoHp());
                        userProfile.put("email", data.getEmail());
                        userProfile.put("alamat", data.getAlamat());
                        userProfile.put("password", data.getPassword());
                        userProfile.put("status", data.getStatus());
                        userProfile.put("tipe_ruang", data.getTipeRuang());

                        session.storeData("userProfile", userProfile.toString());

                        finish();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    loadingDaftar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseUser> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getExtras(){
        apiService.getAllExtras().enqueue(new Callback<ResponseExtras>() {
            @Override
            public void onResponse(Call<ResponseExtras> call, Response<ResponseExtras> response) {
                if (response.body().getStatus().equals("success")){
                    loadingDaftar.dismiss();
                    Extras data = response.body().getExtras();

                    dataExtras = new JSONObject();
                    try {
                        String sliderUrl = "http://dataku.my.id/images/slider/";

                        dataExtras.put("slider_1", sliderUrl + data.getSliderSatu());
                        dataExtras.put("slider_2", sliderUrl + data.getSliderDua());
                        dataExtras.put("slider_3", sliderUrl + data.getSliderTiga());
                        dataExtras.put("slider_4", sliderUrl + data.getSliderEmpat());
                        dataExtras.put("slider_5", sliderUrl + data.getSliderLima());
                        dataExtras.put("visi", data.getVisi());
                        dataExtras.put("misi", data.getMisi());
                        dataExtras.put("tentang", data.getTentang());

                        extras.storeData("dataExtras", dataExtras.toString());

                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    loadingDaftar.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseExtras> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_to_register)
    public void toRegister(){
        startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_to_forgot_password)
    public void toLupaPassword(){
        startActivity(new Intent(getApplicationContext(), LupaPasswordActivity.class));
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
