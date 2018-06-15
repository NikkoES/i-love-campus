package com.ilovecampus.ilovecampus.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.ilovecampus.ilovecampus.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailMemberActivity extends AppCompatActivity {

    @BindView(R.id.txt_id_member)
    TextView txtIdMember;
    @BindView(R.id.txt_nama_member)
    TextView txtNamaMember;
    @BindView(R.id.txt_detail_ruang)
    TextView txtDetailRuang;
    @BindView(R.id.txt_tipe_ruang)
    TextView txtTipeRuang;
    @BindView(R.id.txt_no_hp)
    TextView txtNoHp;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.txt_alamat)
    TextView txtAlamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_member);

        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Member");

        txtIdMember.setText(getIntent().getStringExtra("id_member"));
        txtNamaMember.setText(getIntent().getStringExtra("nama_member"));
        txtDetailRuang.setText(getIntent().getStringExtra("detail_ruang"));
        txtTipeRuang.setText(getIntent().getStringExtra("tipe_ruang"));
        txtNoHp.setText(getIntent().getStringExtra("no_hp"));
        txtEmail.setText(getIntent().getStringExtra("email"));
        txtAlamat.setText(getIntent().getStringExtra("alamat"));
    }

    @OnClick(R.id.btn_whatsapp)
    public void btnWhatsapp(){
        String phone = getIntent().getStringExtra("no_hp");
        String urlWhatsapp = "https://api.whatsapp.com/send?phone="+phone;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlWhatsapp));
        startActivity(intent);
    }

    @OnClick(R.id.btn_call)
    public void btnCall(){
        String phone = getIntent().getStringExtra("no_hp");
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
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