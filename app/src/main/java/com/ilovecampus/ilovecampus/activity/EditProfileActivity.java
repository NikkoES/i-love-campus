package com.ilovecampus.ilovecampus.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.api.BaseApiService;
import com.ilovecampus.ilovecampus.api.UtilsApi;
import com.ilovecampus.ilovecampus.model.data.Member;
import com.ilovecampus.ilovecampus.model.data.Ruang;
import com.ilovecampus.ilovecampus.model.response.ResponsePost;
import com.ilovecampus.ilovecampus.model.response.ResponseRuang;
import com.ilovecampus.ilovecampus.model.response.ResponseUser;
import com.ilovecampus.ilovecampus.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

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
    @BindView(R.id.et_password_lama)
    EditText etPasswordLama;
    @BindView(R.id.et_password_baru)
    EditText etPasswordBaru;
    @BindView(R.id.et_password_baru_2)
    EditText etPasswordBaruDua;

    @BindView(R.id.layout_profile)
    LinearLayout layoutProfile;
    @BindView(R.id.layout_password)
    LinearLayout layoutPassword;

    private SharedPreferencesUtils userDataPref;

    JSONObject userProfile;

    String userData;

    String idMember, nama, detailRuang, idRuang, tipeRuang, noHp, email, alamat, password;

    List<Ruang> listTipe = new ArrayList<>();
    List<String> arrayId = new ArrayList<>();

    BaseApiService apiService;

    private ProgressDialog loadingDaftar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        apiService = UtilsApi.getAPIService();

        userDataPref = new SharedPreferencesUtils(getApplicationContext(), "UserData");

        try {
            userData = userDataPref.getPreferenceData("userProfile");

            userProfile = new JSONObject(userData);
            idMember = userProfile.get("id_member").toString();
            nama = userProfile.get("nama_member").toString();
            detailRuang = userProfile.get("detail_ruang").toString();
            idRuang = userProfile.get("id_ruang").toString();
            tipeRuang = userProfile.get("tipe_ruang").toString();
            noHp = userProfile.get("no_hp").toString();
            email = userProfile.get("email").toString();
            alamat = userProfile.get("alamat").toString();
            password = userProfile.get("password").toString();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if(getIntent().getStringExtra("kode").equalsIgnoreCase("profil")){
            getSupportActionBar().setTitle("Edit Profile");
            layoutProfile.setVisibility(View.VISIBLE);
            layoutPassword.setVisibility(View.GONE);
            etIdMember.setText(idMember);
            etNama.setText(nama);
            etDetailRuang.setText(detailRuang);
            etTipeRuang.setText(tipeRuang);
            etNoHp.setText(noHp);
            etEmail.setText(email);
            etAlamat.setText(alamat);
        }
        else{
            getSupportActionBar().setTitle("Ganti Password");
            layoutProfile.setVisibility(View.GONE);
            layoutPassword.setVisibility(View.VISIBLE);
        }

        loadingDaftar = new ProgressDialog(this);
        loadingDaftar.setTitle("Loading");
        loadingDaftar.setMessage("Menyimpan data...");
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
                    }

                    @Override
                    public void onFailure(Call<ResponseRuang> call, Throwable t) {
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

    @OnClick(R.id.btn_simpan)
    public void btnSimpan(){
        new AlertDialog.Builder(EditProfileActivity.this)
                .setTitle("Apakah anda yakin ingin mengubah ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        loadingDaftar.show();
                        if(getIntent().getStringExtra("kode").equalsIgnoreCase("profil")){
                            if(TextUtils.isEmpty(etNama.getText().toString()) || TextUtils.isEmpty(etDetailRuang.getText().toString()) || TextUtils.isEmpty(etTipeRuang.getText().toString()) || TextUtils.isEmpty(etNoHp.getText().toString()) || TextUtils.isEmpty(etEmail.getText().toString()) || TextUtils.isEmpty(etAlamat.getText().toString())){
                                Toast.makeText(getApplicationContext(), "Data belum lengkap !", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                nama = etNama.getText().toString();
                                detailRuang = etDetailRuang.getText().toString();
                                tipeRuang = etTipeRuang.getText().toString();
                                noHp = etNoHp.getText().toString();
                                email = etEmail.getText().toString();
                                alamat = etAlamat.getText().toString();
                                editProfile(nama, detailRuang, idRuang, noHp, email, alamat);
                            }
                        }
                        else{
                            if(TextUtils.isEmpty(etPasswordLama.getText().toString()) || TextUtils.isEmpty(etPasswordBaru.getText().toString()) || TextUtils.isEmpty(etPasswordBaruDua.getText().toString())){
                                loadingDaftar.dismiss();
                                Toast.makeText(getApplicationContext(), "Data belum lengkap !", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(password.equalsIgnoreCase(etPasswordLama.getText().toString())){
                                    if(etPasswordBaru.getText().toString().equalsIgnoreCase(etPasswordBaruDua.getText().toString())){
                                        ubahPassword(etPasswordBaru.getText().toString());
                                    }
                                    else{
                                        loadingDaftar.dismiss();
                                        Toast.makeText(getApplicationContext(), "Password baru tidak match !", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                                    loadingDaftar.dismiss();
                                    Toast.makeText(getApplicationContext(), "Password lama salah !", Toast.LENGTH_SHORT).show();
                                }
                            }
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

    private void ubahPassword(String password){
        apiService.editPassword(idMember, password)
                .enqueue(new Callback<ResponsePost>() {
                    @Override
                    public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
                        if (response.isSuccessful()){
                            loadingDaftar.dismiss();
                            getUserData(idMember);
                            Toast.makeText(EditProfileActivity.this, "Berhasil mengubah password", Toast.LENGTH_SHORT).show();
                            finish();
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        } else {
                            loadingDaftar.dismiss();
                            Toast.makeText(EditProfileActivity.this, "Gagal mengubah password !", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePost> call, Throwable t) {
                        loadingDaftar.dismiss();
                        Toast.makeText(getApplicationContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void editProfile(String nama, String detailRuang, String idRuang, String noHp, String email, String alamat){
        apiService.editProfile(idMember, nama, detailRuang, idRuang, noHp, email, alamat)
                .enqueue(new Callback<ResponsePost>() {
                    @Override
                    public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
                        if (response.isSuccessful()){
                            loadingDaftar.dismiss();
                            getUserData(idMember);
                            Toast.makeText(EditProfileActivity.this, "Berhasil mengubah profil", Toast.LENGTH_SHORT).show();
                            finish();
                            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        }
                        else {
                            loadingDaftar.dismiss();
                            Toast.makeText(EditProfileActivity.this, "Gagal mengubah profil !", Toast.LENGTH_SHORT).show();
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
        userDataPref.clearAllData();
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

                        userDataPref.storeData("userProfile", userProfile.toString());

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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(EditProfileActivity.this)
                .setTitle("Data tidak akan disimpan ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                new AlertDialog.Builder(EditProfileActivity.this)
                        .setTitle("Data tidak akan disimpan ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                            }
                        })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setCancelable(false)
                        .show();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}