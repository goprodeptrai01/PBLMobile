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
import com.example.shopdientuapp.model.Loaisp;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaispAdapter extends RecyclerView.Adapter<LoaispAdapter.ViewHolder> {
    private static ArrayList<Loaisp> arrayList;
    private static Context context;
    private static GoiSuKienRabenNgoai goiSuKienRabenNgoai;

    public interface GoiSuKienRabenNgoai{
        void onClickMenu(Loaisp loaisp, int vitri);
    }

    public LoaispAdapter(ArrayList<Loaisp> arrayList, Context context, GoiSuKienRabenNgoai goiSuKienRabenNgoai) {
        this.arrayList = arrayList;
        this.context = context;
        this.goiSuKienRabenNgoai = goiSuKienRabenNgoai;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loaisp, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(context).load(arrayList.get(position).getHinhanhloaisp())
                .into(holder.ivLoaisp);

        holder.tvLoaisp.setText(arrayList.get(position).getTenloaisp());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivLoaisp;
        private TextView tvLoaisp;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLoaisp = (ImageView) itemView.findViewById(R.id.iv_loaisp);
            tvLoaisp = (TextView) itemView.findViewById(R.id.tv_loaisp);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goiSuKienRabenNgoai.onClickMenu(arrayList.get(getPosition()), getPosition());
                }
            });
        }
    }
}
