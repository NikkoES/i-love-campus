package com.ilovecampus.ilovecampus.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.api.BaseApiService;
import com.ilovecampus.ilovecampus.api.UtilsApi;
import com.ilovecampus.ilovecampus.model.data.Extras;
import com.ilovecampus.ilovecampus.model.data.Ruang;
import com.ilovecampus.ilovecampus.model.response.ResponsePost;
import com.ilovecampus.ilovecampus.model.response.ResponseRuang;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_id_member)
    EditText etIdMember;
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.et_detail_ruang)
    EditText etDetailRuang;
    @BindView(R.id.et_tipe_ruang)
    EditText etTipeRuang;
    @BindView(R.id.et_no_hp)
    EditText etNoHp;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_alamat)
    EditText etAlamat;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_2)
    EditText etPasswordDua;

    String idMember, nama, detailRuang, idRuang, tipeRuang, noHp, email, alamat, password, passwordDua;

    List<Ruang> listTipe = new ArrayList<>();
    List<String> arrayId = new ArrayList<>();

    final String status = "0";

    private ProgressDialog loadingDaftar;

    BaseApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        apiService = UtilsApi.getAPIService();

        loadingDaftar = new ProgressDialog(this);
        loadingDaftar.setTitle("Loading");
        loadingDaftar.setMessage("Registering your account");
        loadingDaftar.setCancelable(false);
    }

    @OnClick(R.id.et_tipe_ruang)
    public void setTipeRuang(){
        AlertDialog.Builder builderRuang = new AlertDialog.Builder(this);
        builderRuang.setTitle("Pilih tipe ruang :");

        final ArrayAdapter<String> arrayRuang = new ArrayAdapter<String>(this, android.R.layout.select_dialog_singlechoice);
        apiService.getAllRuang()
                .enqueue(new Callback<ResponseRuang>() {
                    @Override
                    public void onResponse(Call<ResponseRuang> call, Response<ResponseRuang> response) {
                        if (response.body().getStatus().equals("success")){
                            listTipe = response.body().getListRuang();
                            for(int i=0; i<listTipe.size(); i++){
                                arrayRuang.add(listTipe.get(i).getTipeRuang());
                                arrayId.add(listTipe.get(i).getIdRuang());
                            }
                        }
                        else {
                            loadingDaftar.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseRuang> call, Throwable t) {
                        loadingDaftar.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });

        builderRuang.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderRuang.setAdapter(arrayRuang, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayRuang.getItem(which);
                etTipeRuang.setText(strName);
                idRuang = arrayId.get(which);
            }
        });
        builderRuang.show();
    }

    @OnClick(R.id.btn_register)
    public void register(){
        if(TextUtils.isEmpty(etIdMember.getText().toString()) || TextUtils.isEmpty(etNama.getText().toString()) || TextUtils.isEmpty(etDetailRuang.getText().toString()) || TextUtils.isEmpty(etTipeRuang.getText().toString()) || TextUtils.isEmpty(etNoHp.getText().toString()) || TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etAlamat.getText().toString()) || TextUtils.isEmpty(etPassword.getText().toString())){
            Toast.makeText(getApplicationContext(), "Data belum lengkap !", Toast.LENGTH_SHORT).show();
        }
        else{
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Anda yakin ingin mendaftar ?")
                    .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            idMember = etIdMember.getText().toString();
                            nama = etNama.getText().toString();
                            detailRuang = etDetailRuang.getText().toString();
                            tipeRuang = etTipeRuang.getText().toString();
                            noHp = etNoHp.getText().toString();
                            email = etEmail.getText().toString();
                            alamat = etAlamat.getText().toString();
                            password = etPassword.getText().toString();
                            passwordDua = etPasswordDua.getText().toString();
                            if(password!=passwordDua){
                                Toast.makeText(getApplicationContext(), "Password tidak match !", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                simpanData(idMember, nama, detailRuang, idRuang, noHp, email, alamat, password);
                            }
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

    private void simpanData(String idMember, String nama, String detailRuang, String idRuang, String noHp, String email, String alamat, String password){
        loadingDaftar.show();
        apiService.register(idMember, nama, detailRuang, idRuang, noHp, email, alamat, password, status)
                .enqueue(new Callback<ResponsePost>() {
                    @Override
                    public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
                        if (response.isSuccessful()){
                            loadingDaftar.dismiss();
                            Toast.makeText(getApplicationContext(), "Registrasi berhasil", Toast.LENGTH_SHORT).show();
                            finish();
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        } else {
                            loadingDaftar.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePost> call, Throwable t) {
                        loadingDaftar.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
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
