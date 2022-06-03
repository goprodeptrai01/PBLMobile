package com.example.shopdientuapp.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopdientuapp.Interface.ItemClickListener;
import com.example.shopdientuapp.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.iv_product);
        txtProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.tv_product_desc);
        txtProductPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view, getAdapterPosition(), false);
    }
}
