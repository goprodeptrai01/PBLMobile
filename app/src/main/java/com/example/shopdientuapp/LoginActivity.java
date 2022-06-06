package com.example.shopdientuapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.shopdientuapp.Prevalent.Prevalent;
import com.example.shopdientuapp.databinding.ActivityLoginBinding;
import com.example.shopdientuapp.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        loadingBar = new ProgressDialog(this);

        Paper.init(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        binding.tvAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnLogin.setText("Login Admin");
                binding.tvAdmin.setVisibility(View.INVISIBLE);
                binding.tvUser.setVisibility(View.VISIBLE);

                parentDbName = "Admins";
            }
        });

        binding.tvUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.btnLogin.setText("Login");
                binding.tvAdmin.setVisibility(View.VISIBLE);
                binding.tvUser.setVisibility(View.INVISIBLE);

                parentDbName = "Users";
            }
        });
    }

    private void loginUser() {
        String phone = binding.edtUser.getText().toString();
        String password = binding.edtPass.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "Please enter your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait,while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            AllowAccessToAccount(phone, password);
        }
    }

    private void AllowAccessToAccount(String phone, String password) {
        if (binding.cbRememberPw.isChecked()) {
            Paper.book().write(Prevalent.UserPhoneKey, phone);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }

        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()) {
                    Users usersdata = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersdata.getPhone().equals(phone)) {
                        if (usersdata.getPassword().equals(password)) {
                            if (parentDbName.equals("Admins")) {
                                Toast.makeText(LoginActivity.this, "Welcome Admin , You are login Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                                Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                Prevalent.currentOnlineUser = new Users("Admin","","");
                                startActivity(intent);
                            } else if (parentDbName.equals("Users")) {
                                Toast.makeText(LoginActivity.this, "Login Successfully...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                                Prevalent.currentOnlineUser = usersdata;
                                startActivity(intent);
                            }
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Your password maybe incorrect.Please try again.", Toast.LENGTH_SHORT).show();
                            loadingBar.dismiss();
                        }
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, "Account with this " + phone + " number do not exists ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}