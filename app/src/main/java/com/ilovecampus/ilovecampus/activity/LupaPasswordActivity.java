package com.ilovecampus.ilovecampus.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.api.BaseApiService;
import com.ilovecampus.ilovecampus.api.UtilsApi;
import com.ilovecampus.ilovecampus.model.data.Member;
import com.ilovecampus.ilovecampus.model.response.ResponsePost;
import com.ilovecampus.ilovecampus.model.response.ResponseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LupaPasswordActivity extends AppCompatActivity {

    @BindView(R.id.et_id_member)
    EditText etIdMember;
    @BindView(R.id.et_email)
    EditText etEmail;

    private ProgressDialog loadingDaftar;

    BaseApiService apiService;

    String idMember, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        ButterKnife.bind(this);

        apiService = UtilsApi.getAPIService();

        loadingDaftar = new ProgressDialog(this);
        loadingDaftar.setTitle("Loading");
        loadingDaftar.setMessage("Sending Password");
        loadingDaftar.setCancelable(false);
    }

    @OnClick(R.id.btn_kirim)
    public void kirimPassword(){
        if(TextUtils.isEmpty(etIdMember.getText().toString()) || TextUtils.isEmpty(etEmail.getText().toString())){
            Toast.makeText(getApplicationContext(), "Data belum lengkap !", Toast.LENGTH_SHORT).show();
        }
        else{
            new AlertDialog.Builder(LupaPasswordActivity.this)
                    .setTitle("Anda yakin ingin mengirim password ?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            idMember = etIdMember.getText().toString();
                            email = etEmail.getText().toString();
                            checkProfile(idMember, email);
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setCancelable(false)
                    .show();
        }
    }

    private void checkProfile(final String idMember, final String email){
        loadingDaftar.show();
        apiService.getUserData(idMember)
                .enqueue(new Callback<ResponseUser>() {
                    @Override
                    public void onResponse(Call<ResponseUser> call, Response<ResponseUser> response) {
                        if (response.isSuccessful()){
                            Member member = response.body().getUser();
                            if(email.equalsIgnoreCase(member.getEmail())){
                                sendPassword(idMember, email, member.getPassword());
                            }
                            else{
                                loadingDaftar.dismiss();
                                Toast.makeText(LupaPasswordActivity.this, "Email yang dimasukkan salah !", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseUser> call, Throwable t) {
                        loadingDaftar.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendPassword(String idMember, String email, String password){
//        apiService.sendPassword("aecf75ca", "Rd9wFsYI79xI0p8j", "I Love Campus Admin", noHp, "Password I Love Campus kamu adalah : "+password)
//                .enqueue(new Callback<ResponsePost>() {
//                    @Override
//                    public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
//                        if (response.isSuccessful()){
//                            loadingDaftar.dismiss();
//                            Toast.makeText(getApplicationContext(), "Password berhasil dikirim", Toast.LENGTH_SHORT).show();
//                            finish();
//                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
//                        }else {
//                            loadingDaftar.dismiss();
//                            Toast.makeText(getApplicationContext(), "Password tidak berhasil dikirim !", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponsePost> call, Throwable t) {
//                        loadingDaftar.dismiss();
//                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
//                    }
//                });
        loadingDaftar.dismiss();
        Toast.makeText(this, "Password anda adalah : "+password, Toast.LENGTH_LONG).show();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_to_login)
    public void toLogin(){
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
