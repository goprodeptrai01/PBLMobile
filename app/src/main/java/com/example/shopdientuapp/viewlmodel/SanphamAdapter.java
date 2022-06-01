package com.example.shopdientuapp.viewlmodel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopdientuapp.R;
import com.example.shopdientuapp.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ViewHolder>{
    private ArrayList<Sanpham> sanphamArrayList;
    private Context context;

    public SanphamAdapter(ArrayList<Sanpham> sanphamArrayList, Context context) {
        this.sanphamArrayList = sanphamArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sanpham_new, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTensp.setText(sanphamArrayList.get(position).getTensanpham());
        holder.tvGiasp.setText(String.valueOf(sanphamArrayList.get(position).getGiasanpham()));

        Picasso.with(context).load(sanphamArrayList.get(position).getHinhansanpham())
                .into(holder.ivSanpham);
    }

    @Override
    public int getItemCount() {
        return sanphamArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivSanpham;
        private TextView tvTensp;
        private TextView tvGiasp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivSanpham = (ImageView) itemView.findViewById(R.id.iv_sanpham);
            tvTensp = (TextView) itemView.findViewById(R.id.tv_tensp);
            tvGiasp = (TextView) itemView.findViewById(R.id.tv_giasp);
        }
    }
}
