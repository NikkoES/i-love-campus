package com.ilovecampus.ilovecampus.fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.activity.DetailKampusActivity;
import com.ilovecampus.ilovecampus.activity.EditProfileActivity;
import com.ilovecampus.ilovecampus.activity.LoginActivity;
import com.ilovecampus.ilovecampus.activity.MainActivity;
import com.ilovecampus.ilovecampus.api.BaseApiService;
import com.ilovecampus.ilovecampus.api.UtilsApi;
import com.ilovecampus.ilovecampus.model.response.ResponsePost;
import com.ilovecampus.ilovecampus.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AkunFragment extends Fragment {

    private SharedPreferencesUtils userDataPref, extrasDataPref, locationPref;

    BaseApiService apiService;

    JSONObject userProfile, dataExtras;

    String userData, extrasData;

    String idMember, nama, detailRuang, tipeRuang, noHp, email, alamat, password, visi, misi, tentang;

    public AkunFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_akun, container, false);

        ButterKnife.bind(this, v);

        apiService = UtilsApi.getAPIService();

        userDataPref = new SharedPreferencesUtils(getContext(), "UserData");
        extrasDataPref = new SharedPreferencesUtils(getContext(), "ExtrasData");
        locationPref = new SharedPreferencesUtils(getContext(), "LocationData");

        try {
            userData = userDataPref.getPreferenceData("userProfile");
            extrasData = extrasDataPref.getPreferenceData("dataExtras");

            userProfile = new JSONObject(userData);
            idMember = userProfile.get("id_member").toString();
            nama = userProfile.get("nama_member").toString();
            detailRuang = userProfile.get("detail_ruang").toString();
            tipeRuang = userProfile.get("tipe_ruang").toString();
            noHp = userProfile.get("no_hp").toString();
            email = userProfile.get("email").toString();
            alamat = userProfile.get("alamat").toString();
            password = userProfile.get("password").toString();

            dataExtras = new JSONObject(extrasData);
            visi = dataExtras.get("visi").toString();
            misi = dataExtras.get("misi").toString();
            tentang = dataExtras.get("tentang").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return v;
    }

    @OnClick(R.id.btn_edit_profile)
    public void btnEditProfile(){
        Intent i = new Intent(getContext(), EditProfileActivity.class);
        i.putExtra("kode", "profil");
        startActivity(i);
        ((Activity) getContext()).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_ubah_password)
    public void btnUbahPassword(){
        Intent i = new Intent(getContext(), EditProfileActivity.class);
        i.putExtra("kode", "password");
        startActivity(i);
        ((Activity) getContext()).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_visi_misi)
    public void btnVisiMisi(){
        Intent i = new Intent(getContext(), DetailKampusActivity.class);
        i.putExtra("kode", "visimisi");
        startActivity(i);
        ((Activity) getContext()).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_tentang_kampus)
    public void btnTentangKampus(){
        Intent i = new Intent(getContext(), DetailKampusActivity.class);
        i.putExtra("kode", "tentangkampus");
        startActivity(i);
        ((Activity) getContext()).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @OnClick(R.id.btn_tutup_aplikasi)
    public void btnTutupAplikasi(){
        new AlertDialog.Builder(getContext())
                .setTitle("Anda yakin ingin menutup aplikasi ini ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        locationPref.removeData("userLocation");
                        clearLocation("keluar");
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();
    }

    private void clearLocation(final String kode){
        apiService.hapusLokasi(idMember)
                .enqueue(new Callback<ResponsePost>() {
                    @Override
                    public void onResponse(Call<ResponsePost> call, Response<ResponsePost> response) {
                        if(kode.equalsIgnoreCase("logout")){
                            getActivity().finish();
                            Intent intentLogin = new Intent(getActivity(), LoginActivity.class);
                            startActivity(intentLogin);
                            ((Activity) getContext()).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                        }
                        else{
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePost> call, Throwable t) {
                        Toast.makeText(getContext(), "Koneksi internet bermasalah !", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @OnClick(R.id.btn_logout)
    public void btnLogout(){
        new AlertDialog.Builder(getContext())
                .setTitle("Anda yakin ingin keluar ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userDataPref.removeData("userProfile");
                        extrasDataPref.removeData("dataExtras");
                        locationPref.removeData("userLocation");
                        clearLocation("logout");
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setCancelable(false)
                .show();
    }

}
