package com.ilovecampus.ilovecampus.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ilovecampus.ilovecampus.R;
import com.ilovecampus.ilovecampus.activity.DetailMemberActivity;
import com.ilovecampus.ilovecampus.model.data.Member;

import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {

    private Context context;
    private List<Member> listMember;

    public MemberAdapter(Context context, List<Member> listMember){
        this.context = context;
        this.listMember = listMember;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_member, null, false);

        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(layoutParams);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Member member = listMember.get(position);
        holder.txtNamaMember.setText(member.getNamaMember());
        holder.txtIdMember.setText(member.getIdMember());
    }

    @Override
    public int getItemCount() {
        return listMember.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtNamaMember, txtIdMember;

        public ViewHolder(View itemView) {
            super(itemView);

            txtNamaMember = (TextView) itemView.findViewById(R.id.txt_nama_member);
            txtIdMember = (TextView) itemView.findViewById(R.id.txt_id_member);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int mPosition = getLayoutPosition();
                    Member member = listMember.get(mPosition);

                    Intent i = new Intent(context, DetailMemberActivity.class);
                    i.putExtra("id_member", member.getIdMember());
                    i.putExtra("nama_member", member.getNamaMember());
                    i.putExtra("detail_ruang", member.getDetailRuang());
                    i.putExtra("tipe_ruang", member.getTipeRuang());
                    i.putExtra("no_hp", member.getNoHp());
                    i.putExtra("email", member.getEmail());
                    i.putExtra("alamat", member.getAlamat());
                    context.startActivity(i);
                }
            });
        }
    }

}
