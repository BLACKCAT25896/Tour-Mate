package com.example.tourmate;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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

public class MemoryActivity extends AppCompatActivity {
    private ActivityMemoryBinding binding;

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

                    //binding.showIV.setImageURI(selectedImage);
                    binding.showImageL.setVisibility(binding.showImageL.VISIBLE);
                    binding.addImageIV.setVisibility(binding.addImageIV.INVISIBLE);

                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    binding.showIV.setImageURI(selectedImage);
                    binding.showImageL.setVisibility(binding.showImageL.VISIBLE);
                    binding.addImageIV.setVisibility(binding.addImageIV.INVISIBLE);

                }
                break;
        }



    }

    private void init() {
    }

    public void cancelImagePicker(View view) {
        finish();
    }
}
