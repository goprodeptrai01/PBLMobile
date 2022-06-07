package com.example.shopdientuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shopdientuapp.Prevalent.Prevalent;
import com.example.shopdientuapp.databinding.ActivityProductDetailBinding;
import com.example.shopdientuapp.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private String productID  = "",state = "Normal";
    private int quantity = 1;
    private String saveCurrentDate, saveCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        productID = getIntent().getStringExtra("pid");

        getProductDetails(productID);

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quantity++;
                binding.tvQuantity.setText(String.valueOf(quantity));
            }
        });

        binding.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( quantity > 0) {
                    quantity--;
                    binding.tvQuantity.setText(String.valueOf(quantity));
                }
            }
        });

        binding.pdAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state.equals("Order Placed") || state.equals("Order Shipped")){
                    Toast.makeText(ProductDetailActivity.this, "you can add purchase more products, once your order is shipped or confirmed!", Toast.LENGTH_LONG).show();
                }
                else {
                    addingToCartList();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        
        CheckOrderState();
    }

    private void addingToCartList() {


        Log.d("Debug", "loi2");

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Cart List");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("name", binding.productNameDetails.getText().toString());
        cartMap.put("price", binding.productPriceDetails.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("quantity", binding.tvQuantity.getText().toString());
        cartMap.put("discount", "");

        Log.d("Debug", "loi3");

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                .child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone()).child("Products")
                                    .child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){


                                                Toast.makeText(ProductDetailActivity.this,  "Added to cart List", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(ProductDetailActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef  = FirebaseDatabase.getInstance().getReference().child("Products");

        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Products products =  snapshot.getValue(Products.class);

                    binding.productNameDetails.setText(products.getName());
                    binding.productDescriptionDetails.setText(products.getDescription());
                    binding.productPriceDetails.setText(products.getPrice() + " vnd");
                    Picasso.get().load(products.getImage()).into(binding.productImageDetails);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void CheckOrderState(){
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String shippingState = snapshot.child("state").getValue().toString();

                    if (shippingState.equals("shipped")){
                        state = "Order Shipped";
                    }
                    else if (shippingState.equals("not shipped")){
                        state = "Order Placed";

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}