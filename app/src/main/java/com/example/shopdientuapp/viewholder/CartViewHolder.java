package com.example.shopdientuapp.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shopdientuapp.Interface.ItemClickListener;
import com.example.shopdientuapp.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
    public TextView txtProductName;
    public TextView txtProductPrice;
    public TextView txtProductQuantity;
    private ItemClickListener itemClickListener;

    public CartViewHolder(View itemView) {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.tv_name);
        txtProductPrice = itemView.findViewById(R.id.tv_price);
        txtProductQuantity = itemView.findViewById(R.id.tv_quantity);
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAbsoluteAdapterPosition(), false);
    }
}
