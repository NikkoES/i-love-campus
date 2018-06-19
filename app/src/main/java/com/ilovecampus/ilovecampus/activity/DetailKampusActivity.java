package com.ilovecampus.ilovecampus.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.utils.SharedPreferencesUtils;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailKampusActivity extends AppCompatActivity {

    @BindView(R.id.layout_visi_misi)
    LinearLayout layoutVisiMisi;
    @BindView(R.id.layout_tentang_kampus)
    LinearLayout layoutTentangKampus;

    @BindView(R.id.txt_visi)
    TextView txtVisi;
    @BindView(R.id.txt_misi)
    TextView txtMisi;
    @BindView(R.id.txt_tentang_kampus)
    TextView txtTentangKampus;

    String visi, misi, tentang;

    private SharedPreferencesUtils extrasDataPref;

    JSONObject dataExtras;

    String extrasData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_kampus);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        extrasDataPref = new SharedPreferencesUtils(this, "ExtrasData");

        try {
            extrasData = extrasDataPref.getPreferenceData("dataExtras");

            dataExtras = new JSONObject(extrasData);
            visi = dataExtras.get("visi").toString();
            misi = dataExtras.get("misi").toString();
            tentang = dataExtras.get("tentang").toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if(getIntent().getStringExtra("kode").equalsIgnoreCase("visimisi")){
            getSupportActionBar().setTitle("Visi & Misi");
            layoutVisiMisi.setVisibility(View.VISIBLE);
            layoutTentangKampus.setVisibility(View.GONE);
            txtVisi.setText(Html.fromHtml(visi));
            txtMisi.setText(Html.fromHtml(misi));
        }
        else{
            getSupportActionBar().setTitle("Tentang Kampus");
            layoutVisiMisi.setVisibility(View.GONE);
            layoutTentangKampus.setVisibility(View.VISIBLE);
            txtTentangKampus.setText(Html.fromHtml(tentang));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home : {
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
