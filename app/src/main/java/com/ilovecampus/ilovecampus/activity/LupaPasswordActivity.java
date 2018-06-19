package com.ilovecampus.ilovecampus.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.api.BaseApiService;
import com.ilovecampus.ilovecampus.api.UtilsApi;
import com.ilovecampus.ilovecampus.model.data.Member;
import com.ilovecampus.ilovecampus.model.response.ResponseUser;
import com.ilovecampus.ilovecampus.utils.GmailSender;

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

    private void sendPassword(final String idMember, final String email, final String password){
        final String emailAdmin = "pujilestariku1992@gmail.com";
        final String passwordAdmin = "pujilestariku92";
        loadingDaftar.dismiss();
        final ProgressDialog dialog = new ProgressDialog(LupaPasswordActivity.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GmailSender sender = new GmailSender(emailAdmin, passwordAdmin);
                    sender.sendMail("Password Member I Love Campus",
                            "Berikut adalah data akun anda : \n" +
                                    "ID Member : "+idMember+"\n" +
                                    "Password : "+password,
                            emailAdmin,
                            email);
                    dialog.dismiss();
                    finish();
                    Toast.makeText(LupaPasswordActivity.this, "Password berhasil dikirim", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                    dialog.dismiss();
                    Toast.makeText(LupaPasswordActivity.this, "Password gagal dikirim !", Toast.LENGTH_SHORT).show();
                }
            }
        });
        sender.start();
    }

    private void sendMessage() {

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
