package com.example.shopdientuapp.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.shopdientuapp.R;
import com.example.shopdientuapp.databinding.FragmentHomeBinding;
import com.example.shopdientuapp.model.Loaisp;
import com.example.shopdientuapp.viewlmodel.LoaispAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    //loai san pham
    private ArrayList<Loaisp> loaispArrayList;
    private LoaispAdapter loaispAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Loai san pham
        loaispArrayList = new ArrayList<>();
        loaispAdapter = new LoaispAdapter(loaispArrayList, getContext(), new LoaispAdapter.GoiSuKienRabenNgoai() {
            @Override
            public void onClickMenu(Loaisp loaisp, int vitri) {
                binding.drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
        binding.rvTbloaisp.setAdapter(loaispAdapter);
        binding.rvTbloaisp.setLayoutManager(new LinearLayoutManager(getContext()));

        ActionBar();
        ActionViewFlipper();
        GetDataLoaisp();

    }

    private void GetDataLoaisp() {
        loaispArrayList.add(0, new Loaisp(0, "Trang Chính", "https://icons.iconarchive.com/icons/fps.hu/free-christmas-flat-circle/512/home-icon.png"));
        loaispArrayList.add(1, new Loaisp(30, "Liên Hệ", "https://cdn1.iconfinder.com/data/icons/mix-color-3/502/Untitled-12-512.png"));
        loaispArrayList.add(2, new Loaisp(40, "Thông Tin", "https://cdn2.iconfinder.com/data/icons/perfect-flat-icons-2/512/User_info_man_male_profile_information.png"));
        loaispAdapter.notifyDataSetChanged();
    }

    private void ActionViewFlipper() {
        ArrayList<String> manhinhquangcao = new ArrayList<>();
        manhinhquangcao.add("https://image-us.24h.com.vn/upload/4-2018/images/2018-11-29/1543483835-558-2-1543470704-width660height385.jpg");
        manhinhquangcao.add("https://i2.wp.com/techtimes.vn/wp-content/uploads/2019/02/Galaxy-s10e-techtimes.jpg");
        manhinhquangcao.add("https://img.global.news.samsung.com/vn/wp-content/uploads/2021/05/KV-final-12-1024x666.jpg");
        manhinhquangcao.add("https://adsplus.vn/wp-content/uploads/2017/05/quang-cao-tren-mobile-mang-lai-su-thanh-cong-cho-doanh-nghiep-cua-ban-1.jpg");

        for(int i = 0; i < manhinhquangcao.size(); i++){
            ImageView imageView = new ImageView(getContext());
            Picasso.with(getContext()).load(manhinhquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.vfManhinhchinh.addView(imageView);
        }

        binding.vfManhinhchinh.setFlipInterval(5000);
        binding.vfManhinhchinh.setAutoStart(true);

        Animation animation_slide_in = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getContext(), R.anim.slide_out_right);

        binding.vfManhinhchinh.setInAnimation(animation_slide_in);
        binding.vfManhinhchinh.setOutAnimation(animation_slide_out);
    }


    private void ActionBar(){
        ((AppCompatActivity)getActivity()).setSupportActionBar(binding.tbManhinhchinh);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.tbManhinhchinh.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);

        binding.tbManhinhchinh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}