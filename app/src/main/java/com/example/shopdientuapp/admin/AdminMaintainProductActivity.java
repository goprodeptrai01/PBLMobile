package com.example.shopdientuapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.shopdientuapp.databinding.ActivityAdminMaintainProductBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class AdminMaintainProductActivity extends AppCompatActivity {

    private ActivityAdminMaintainProductBinding binding;
    private String productID = "";
    private DatabaseReference productsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMaintainProductBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        productID = getIntent().getStringExtra("pid");
        productsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(productID);

        displayProductInformation();

        binding.btnEditProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProduct();
            }
        });

        binding.btnDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct();
            }
        });
    }

    private void deleteProduct() {

        productsRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Intent intent = new Intent(AdminMaintainProductActivity.this, AdminCategoryActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(AdminMaintainProductActivity.this, "Product Deleted ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editProduct() {
        String editName = binding.edtEditName.getText().toString();
        String editDesc = binding.edtEditDesc.getText().toString();
        String editPrice = binding.edtEditPrice.getText().toString();

        if(editName.equals("")) {
            Toast.makeText(this, "Please enter the name", Toast.LENGTH_SHORT).show();
        }
        else if (editPrice.equals("")) {
            Toast.makeText(this, "Please enter the price", Toast.LENGTH_SHORT).show();
        }
        else if (editDesc.equals("")) {
            Toast.makeText(this, "Please enter the description", Toast.LENGTH_SHORT).show();
        }
        else {
            HashMap<String, Object> productMap = new HashMap<>();
            productMap.put("pid",productID);
            productMap.put("description",editDesc);
            productMap.put("price",editPrice);
            productMap.put("name",editName);

            productsRef.updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AdminMaintainProductActivity.this, "Edited", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(AdminMaintainProductActivity.this, AdminCategoryActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

    private void displayProductInformation() {
        productsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String name = snapshot.child("name").getValue().toString();
                    String description = snapshot.child("description").getValue().toString();
                    String price = snapshot.child("price").getValue().toString();
                    String image = snapshot.child("image").getValue().toString();

                    binding.edtEditName.setText(name);
                    binding.edtEditDesc.setText(description);
                    binding.edtEditPrice.setText(price);
                    Picasso.get().load(image).into(binding.ivEditAnhProduct);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}