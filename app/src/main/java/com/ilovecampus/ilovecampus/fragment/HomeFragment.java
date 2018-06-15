package com.ilovecampus.ilovecampus.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.activity.MapsActivity;
import com.ilovecampus.ilovecampus.activity.RegisterActivity;
import com.ilovecampus.ilovecampus.adapter.ImageSlideAdapter;
import com.ilovecampus.ilovecampus.adapter.MemberAdapter;
import com.ilovecampus.ilovecampus.api.BaseApiService;
import com.ilovecampus.ilovecampus.api.UtilsApi;
import com.ilovecampus.ilovecampus.model.data.Lokasi;
import com.ilovecampus.ilovecampus.model.response.ResponseLokasi;
import com.ilovecampus.ilovecampus.model.response.ResponseMember;
import com.ilovecampus.ilovecampus.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.home_slider)
    ViewPager homeSlider;

    @BindView(R.id.ci_indicator)
    CircleIndicator mCiIndicator;

    private ImageSlideAdapter mImageSlideAdapter = null;

    private SharedPreferencesUtils userDataSharedPreferences;

    JSONObject dataExtras;

    String extrasData;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this,v);

        initViewPager();

        return v;
    }

    @OnClick(R.id.btn_lokasi_member)
    public void btnLokasiMember(){
        Intent i = new Intent(getContext(), MapsActivity.class);
        startActivity(i);

        ((Activity) getContext()).overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    private void initViewPager() {
        userDataSharedPreferences = new SharedPreferencesUtils(getContext(), "ExtrasData");

        try {
            extrasData = userDataSharedPreferences.getPreferenceData("dataExtras");
            dataExtras = new JSONObject(extrasData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        List<String> data = new ArrayList<>();

        try {
            data.add(dataExtras.get("slider_1").toString());
            data.add(dataExtras.get("slider_2").toString());
            data.add(dataExtras.get("slider_3").toString());
            data.add(dataExtras.get("slider_4").toString());
            data.add(dataExtras.get("slider_5").toString());
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        mImageSlideAdapter = new ImageSlideAdapter(data);
        homeSlider.setAdapter(mImageSlideAdapter);
        mCiIndicator.setViewPager(homeSlider);
        mImageSlideAdapter.registerDataSetObserver(mCiIndicator.getDataSetObserver());
    }

}
