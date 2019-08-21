package com.example.tourmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.example.tourmate.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {
    private ActivitySplashBinding binding;
    private int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();

                startActivity(new Intent(SplashActivity.this,EmailSignInActivity.class));
                finish();

            }
        });
        thread.start();


    }

    private void doWork() {

        for(progress=0;progress <= 100;progress= progress + 1){

            try {
                Thread.sleep(50);
                binding.pg.setProgress(progress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }




    }
}
