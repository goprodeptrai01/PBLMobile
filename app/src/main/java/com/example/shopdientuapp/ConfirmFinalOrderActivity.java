package com.example.shopdientuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopdientuapp.Prevalent.Prevalent;
import com.example.shopdientuapp.databinding.ActivityConfirmFinalOrderBinding;
import com.example.shopdientuapp.databinding.ActivityProductDetailBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmFinalOrderActivity extends AppCompatActivity {
    private ActivityConfirmFinalOrderBinding binding;

    private String totalAmount = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmFinalOrderBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        totalAmount = getIntent().getStringExtra("Total Price");
        Toast.makeText(this, "Total Price = "+ totalAmount + " vnd", Toast.LENGTH_SHORT).show();

        binding.edtName.setText(Prevalent.currentOnlineUser.getName());
        binding.edtPhonenumber.setText(Prevalent.currentOnlineUser.getPhone());
        binding.edtAddress.setText(Prevalent.currentOnlineUser.getAddress());
        binding.tvTotalPrc.setText("Total Price: "+totalAmount + " vnd");
        binding.tvTotalPrc.setEnabled(false);

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check() {
        if (TextUtils.isEmpty(binding.edtName.getText().toString())){
            Toast.makeText(this, "Please provide your full name.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(binding.edtPhonenumber.getText().toString())){
            Toast.makeText(this, "Please provide your phone number.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(binding.edtAddress.getText().toString())){
            Toast.makeText(this, "Please provide your address.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(binding.edtYourCity.getText().toString())){
            Toast.makeText(this, "Please provide your city.", Toast.LENGTH_SHORT).show();
        }
        else {
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        final String saveCurrentDate;
        final String saveCurrentTime;

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevalent.currentOnlineUser.getPhone());

        final HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("totalAmount", totalAmount);
        ordersMap.put("name", binding.edtName.getText().toString());
        ordersMap.put("phone", binding.edtPhonenumber.getText().toString());
        ordersMap.put("address", binding.edtAddress.getText().toString());
        ordersMap.put("city", binding.edtYourCity.getText().toString());
        ordersMap.put("date", saveCurrentDate);
        ordersMap.put("time", saveCurrentTime);
        ordersMap.put("state","not shipped");

        ordersRef.updateChildren(ordersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("Cart List")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ConfirmFinalOrderActivity.this, "your final order has been placed successfully!", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(ConfirmFinalOrderActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });
                }
            }
        });
    }
}