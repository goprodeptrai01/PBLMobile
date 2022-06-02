package com.example.shopdientuapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shopdientuapp.R;
import com.example.shopdientuapp.databinding.FragmentAdminCategoryBinding;
import com.example.shopdientuapp.databinding.FragmentLoginBinding;

public class AdminCategoryFragment extends Fragment {

    private FragmentAdminCategoryBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.ivAddlaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","laptop");
                Navigation.findNavController(binding.ivAddlaptop).navigate(R.id.addProductFragment, bundle);

            }
        });

        binding.ivAddphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","mobile");
                Navigation.findNavController(binding.ivAddlaptop).navigate(R.id.addProductFragment, bundle);
            }
        });

        binding.ivTablet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","tablet");
                Navigation.findNavController(binding.ivAddlaptop).navigate(R.id.addProductFragment, bundle);
            }
        });

        binding.ivWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","watch");
                Navigation.findNavController(binding.ivAddlaptop).navigate(R.id.addProductFragment, bundle);
            }
        });

        binding.ivHeadphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("category","headphone");
                Navigation.findNavController(binding.ivAddlaptop).navigate(R.id.addProductFragment, bundle);
            }
        });
    }
}