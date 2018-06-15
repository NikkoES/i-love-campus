package com.ilovecampus.ilovecampus.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.adapter.MemberAdapter;
import com.ilovecampus.ilovecampus.api.BaseApiService;
import com.ilovecampus.ilovecampus.api.UtilsApi;
import com.ilovecampus.ilovecampus.model.data.Member;
import com.ilovecampus.ilovecampus.model.response.ResponseMember;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PencarianFragment extends Fragment implements SearchView.OnQueryTextListener {

    @BindView(R.id.rv_member)
    RecyclerView rvMember;

    @BindView(R.id.search_member)
    SearchView searchMember;

    private MemberAdapter adapter;
    List<Member> listMember = new ArrayList<>();

    BaseApiService apiService;

    private final String status = "1";

    public PencarianFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pencarian, container, false);

        ButterKnife.bind(this, v);

        apiService = UtilsApi.getAPIService();

        adapter = new MemberAdapter(getContext(), listMember);

        rvMember.setHasFixedSize(true);
        rvMember.setLayoutManager(new LinearLayoutManager(getContext()));
        rvMember.setAdapter(adapter);

        searchMember.setQueryHint("Pencarian ID");
        searchMember.setIconifiedByDefault(false);
        searchMember.setOnQueryTextListener(this);

        refresh();

        return v;
    }

    private void refresh(){
        apiService.getAllMember(status).enqueue(new Callback<ResponseMember>() {
            @Override
            public void onResponse(Call<ResponseMember> call, Response<ResponseMember> response) {
                if (response.isSuccessful()){

                    listMember = response.body().getListMember();

                    rvMember.setAdapter(new MemberAdapter(getContext(), listMember));
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMember> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void actionSearchMember(String keyword){
        apiService.searchMember(keyword, status).enqueue(new Callback<ResponseMember>() {
            @Override
            public void onResponse(Call<ResponseMember> call, Response<ResponseMember> response) {
                if (response.isSuccessful()){
                    listMember = response.body().getListMember();

                    rvMember.setAdapter(new MemberAdapter(getContext(), listMember));
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getContext(), "Failed to Fetch Data !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMember> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to Connect Internet !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        actionSearchMember(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)){
            refresh();
        }
        else{
            actionSearchMember(newText);
        }
        return false;
    }

}
