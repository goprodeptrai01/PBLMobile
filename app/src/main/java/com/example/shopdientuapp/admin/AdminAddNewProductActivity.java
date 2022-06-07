package com.example.shopdientuapp.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.shopdientuapp.databinding.ActivityAdminAddNewProductBinding;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private ActivityAdminAddNewProductBinding binding;
    private String CategoryName;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String saveCurrentDate, saveCurrentTime;
    private String ProductRandomKey, downloadImageUrl;
    private StorageReference ProductImageRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminAddNewProductBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        loadingBar = new ProgressDialog(this);

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();

        binding.ivAnhProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==GalleryPick && resultCode == Activity.RESULT_OK && data != null) {
            ImageUri = data.getData();
            binding.ivAnhProduct.setImageURI(ImageUri);
        }
    }

    private void ValidateProductData() {
        if ( ImageUri == null) {
            Toast.makeText(this, "Product image is mandatory....", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(binding.editDesc.getText())) {
            Toast.makeText(this, "Please write the product description", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(binding.editName.getText())) {
            Toast.makeText(this, "Please enter the product name", Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(binding.editPrice.getText())) {
            Toast.makeText(this, "Please enter the product price", Toast.LENGTH_SHORT).show();
        }
        else {
            StoreProductInformation();
        }
    }

    private void StoreProductInformation() {

        loadingBar.setTitle("Add new product");
        loadingBar.setMessage("Please wait,while we are adding new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        ProductRandomKey = saveCurrentDate + saveCurrentTime;

        StorageReference filepath = ProductImageRef.child(ImageUri.getLastPathSegment() + ProductRandomKey + ".jpg");

        final UploadTask uploadTask = filepath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error : " + message , Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "Image uploaded successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        downloadImageUrl = filepath.getDownloadUrl().toString();
                        return  filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddNewProductActivity.this, "Got the product image url", Toast.LENGTH_SHORT).show();
                            SaveProductInfoToDatabase(binding.editName.getText().toString());
                        }
                    }
                });
            }
        });

    }

    private void SaveProductInfoToDatabase(String name) {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid",ProductRandomKey);
        productMap.put("date",saveCurrentDate);
        productMap.put("time",saveCurrentTime);
        productMap.put("description",binding.editDesc.getText().toString());
        productMap.put("image",downloadImageUrl);
        productMap.put("category",CategoryName);
        productMap.put("name",binding.editName.getText().toString());
        productMap.put("price",binding.editPrice.getText().toString());

        Log.e("Debug","K bi loi ne me r");
        ProductsRef.child(ProductRandomKey.toString()).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewProductActivity.this, "Product is added", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);
                        }
                        else {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Log.e("Debug","K bi loi ne me r");

                            Toast.makeText(AdminAddNewProductActivity.this, "Error : " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}