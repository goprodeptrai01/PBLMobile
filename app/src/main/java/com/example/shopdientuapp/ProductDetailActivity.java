package com.example.shopdientuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.shopdientuapp.databinding.ActivityLoginBinding;
import com.example.shopdientuapp.databinding.ActivityProductDetailBinding;
import com.example.shopdientuapp.model.Products;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private String productID  = "";
    private int quantity = 1;

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
}