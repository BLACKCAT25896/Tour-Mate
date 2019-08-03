package com.example.tourmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tourmate.databinding.ActivityEmailSignInBinding;

public class EmailSignInActivity extends AppCompatActivity {
    private ActivityEmailSignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_email_sign_in);

        init();





        binding.signUpTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailSignInActivity.this, EmailSignUpActivity.class));
            }
        });

        binding.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmailSignInActivity.this, LoginActivity.class));
            }
        });




    }

    private void init() {

    }

    public void mainActivity(View view) {
        startActivity(new Intent(EmailSignInActivity.this, MainActivity.class));
    }


    public void nearBy(View view) {
        startActivity(new Intent(EmailSignInActivity.this, NearByActivity.class));

    }
}
