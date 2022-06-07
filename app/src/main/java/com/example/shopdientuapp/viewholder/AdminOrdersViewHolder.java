package com.example.shopdientuapp.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.shopdientuapp.R;

public class AdminOrdersViewHolder extends RecyclerView.ViewHolder {
    public TextView userName;
    public TextView userPhone;
    public TextView userTotalPrice;
    public TextView userDateTime;
    public TextView userShippingAddress;
    public Button showOrdersBtn;

    public AdminOrdersViewHolder(View itemView){
        super(itemView);

        userName = itemView.findViewById(R.id.tv_userName);
        userPhone = itemView.findViewById(R.id.tv_phoneNumber);
        userTotalPrice = itemView.findViewById(R.id.tv_totalPrice);
        userDateTime = itemView.findViewById(R.id.tv_datetime);
        userShippingAddress = itemView.findViewById(R.id.tv_address);
        showOrdersBtn = itemView.findViewById(R.id.btn_show);
    }
}
