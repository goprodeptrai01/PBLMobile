package com.example.shopdientuapp.view;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NavigationRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shopdientuapp.R;
import com.example.shopdientuapp.databinding.FragmentHomeBinding;
import com.example.shopdientuapp.databinding.FragmentLoginBinding;
import com.example.shopdientuapp.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private ProgressDialog loadingBar;
    private String parentDbName = "Users";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadingBar = new ProgressDialog(getActivity());

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Bundle bundle = new Bundle();
//                Navigation.findNavController(binding.btnLogin).navigate(R.id.homeFragment);
                loginUser();
            }
        });
    }

    private void loginUser() {
        String phone = binding.edtUser.getText().toString();
        String password = binding.edtPass.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getActivity(), "Please enter your phone number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Please enter your password...", Toast.LENGTH_SHORT).show();
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
        final DatabaseReference RootRef;
        RootRef = FirebaseDatabase.getInstance().getReference();


        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(parentDbName).child(phone).exists()) {
                    Users usersdata = snapshot.child(parentDbName).child(phone).getValue(Users.class);

                    if (usersdata.getPhone().equals(phone) && usersdata.getPassword().equals(password)) {
                        Toast.makeText(getActivity(), "Login Successfully...", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();

                        Navigation.findNavController(binding.btnLogin).navigate(R.id.homeFragment);
                    }
                    else {
                        Toast.makeText(getActivity(), "Your password maybe incorrect.Please try again.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }
                else {
                    Toast.makeText(getActivity(), "Account with this " + phone + "number do not exists ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}