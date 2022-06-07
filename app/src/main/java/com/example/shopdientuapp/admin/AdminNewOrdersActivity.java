package com.example.shopdientuapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopdientuapp.HomeActivity;
import com.example.shopdientuapp.R;
import com.example.shopdientuapp.ResetPasswordActivity;
import com.example.shopdientuapp.databinding.ActivityAdminNewOrdersBinding;
import com.example.shopdientuapp.model.AdminOrders;
import com.example.shopdientuapp.viewholder.AdminOrdersViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminNewOrdersActivity extends AppCompatActivity {
    private ActivityAdminNewOrdersBinding binding;
    private DatabaseReference ordersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminNewOrdersBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders");

        binding.orderList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminOrders> options = new FirebaseRecyclerOptions
                .Builder<AdminOrders>()
                .setQuery(ordersRef, AdminOrders.class)
                .build();
        FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder> adapter = new FirebaseRecyclerAdapter<AdminOrders, AdminOrdersViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull AdminOrders model) {
                holder.userName.setText("Name: "+ model.getName());
                holder.userPhone.setText("Phone: "+ model.getPhone());
                holder.userTotalPrice.setText("Total Amount: "+ model.getTotalAmount() + "vnd");
                holder.userDateTime.setText("Order at: "+ model.getDate() + " "+ model.getTime());
                holder.userShippingAddress.setText("Shiping address: "+ model.getAddress() + ", " + model.getCity());

                holder.showOrdersBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String uID = getRef(position).getKey();

                        Intent intent = new Intent(AdminNewOrdersActivity.this, AdminUserProductsActivity.class);
                        intent.putExtra("uid", uID);
                        startActivity(intent);
                    }
                });

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CharSequence options[] = new CharSequence[]{
                                "Yes",
                                "No"
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(AdminNewOrdersActivity.this);
                        builder.setTitle("Have you shipped this order products ?");

                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (i == 0){
                                    String uID = getRef(position).getKey();

                                    RemoverOrder(uID);
                                }
                                else {

//                                    finish();
                                    Intent intent = new Intent(AdminNewOrdersActivity.this, AdminNewOrdersActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });

                        builder.show();
                    }
                });
            }

            @NonNull
            @Override
            public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false);
                return new AdminOrdersViewHolder(view);
            }
        };

        binding.orderList.setAdapter(adapter);
        adapter.startListening();
    }

    private void RemoverOrder(String uID) {
        ordersRef.child(uID).removeValue();
    }

}
