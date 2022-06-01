package com.example.shopdientuapp.viewlmodel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopdientuapp.R;
import com.example.shopdientuapp.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DienthoaiAdapter extends RecyclerView.Adapter<DienthoaiAdapter.ViewHolder>{
    private static ArrayList<Sanpham> arrayList;
    private static Context context;

    public DienthoaiAdapter(ArrayList<Sanpham> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dienthoai,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(arrayList.get(position).getHinhansanpham()).into(holder.ivDienthoai);
        holder.tvTendienthoai.setText(arrayList.get(position).getTensanpham());
        holder.tvGiadienthoai.setText(String.valueOf(arrayList.get(position).getGiasanpham()));
        holder.tvMota.setText(arrayList.get(position).getMotasanpham());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivDienthoai;
        private TextView tvTendienthoai;
        private TextView tvGiadienthoai;
        private TextView tvMota;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivDienthoai = (ImageView) itemView.findViewById(R.id.iv_dienthoai);
            tvTendienthoai = (TextView) itemView.findViewById(R.id.tv_tendienthoai);
            tvGiadienthoai = (TextView) itemView.findViewById(R.id.tv_giadienthoai);
            tvMota = (TextView) itemView.findViewById(R.id.tv_motadienthoai);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
