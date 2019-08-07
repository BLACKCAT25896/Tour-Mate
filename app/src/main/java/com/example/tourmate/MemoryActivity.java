package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.tourmate.databinding.ActivityMemoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class MemoryActivity extends AppCompatActivity {
    private ActivityMemoryBinding binding;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String imageUrl="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_memory);
        init();


        binding.addImageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(MemoryActivity.this, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()) {
                            case R.id.camera:

                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,0);
                                return true;
                            case R.id.gallery:
                                Intent intent1 = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(intent1,1);
                                return true;
                            default:
                        }

                        return false;
                    }
                });
                popupMenu.inflate(R.menu.camera_galary);
                popupMenu.show();


            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    //Uri selectedImage = data.getData();
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    binding.showIV.setImageBitmap(bitmap);
                   Uri uri = getImageUri(this,bitmap);
                   uploadImageToStorage(uri);

                    binding.showImageL.setVisibility(binding.showImageL.VISIBLE);
                    binding.addImageIV.setVisibility(binding.addImageIV.INVISIBLE);

                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri uri = data.getData();
                    binding.showIV.setImageURI(uri);
                    binding.showImageL.setVisibility(binding.showImageL.VISIBLE);
                    binding.addImageIV.setVisibility(binding.addImageIV.INVISIBLE);
                    uploadImageToStorage(uri);

                }
                break;
        }



    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void uploadImageToStorage(Uri uri) {


        final StorageReference memoryImageRef = storageReference.child(String.valueOf(System.currentTimeMillis()));
        memoryImageRef.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
             if(task.isSuccessful()){
                    memoryImageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageUrl = uri.toString();
                            Toast.makeText(MemoryActivity.this, "Successssss", Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(MemoryActivity.this,ShowMemoryActivity.class));

                        }
                    });
             }
            }
        });
    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void cancelImagePicker(View view) {
        onBackPressed();
    }
}
