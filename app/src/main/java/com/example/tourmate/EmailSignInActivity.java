package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.tourmate.databinding.ActivityEmailSignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmailSignInActivity extends AppCompatActivity {
    private ActivityEmailSignInBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String email, password;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_email_sign_in);

        init();

        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(EmailSignInActivity.this,HomeActivity.class));
        }




        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = binding.signInEmailET.getText().toString();
                password = binding.signInPasswordET.getText().toString();
                email = binding.signInEmailET.getText().toString().trim();

                if (email.isEmpty()) {
                    binding.signInEmailET.setError("please input email !");

                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                    binding.signInEmailET.setError("please enter valid email address !");

                }
                password = binding.signInPasswordET.getText().toString().trim();
                if (password.isEmpty()) {
                    binding.signInPasswordET.setError("Please input password !");

                } else if (password.length() < 6) {
                    binding.signInPasswordET.setError("password 6 digit or more");


                } else {

                    login(email, password);

                }





            }
        });





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

    private void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   // FirebaseUser user = firebaseAuth.getCurrentUser();
                    startActivity(new Intent(EmailSignInActivity.this, HomeActivity.class));
                    finish();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EmailSignInActivity.this, ""+ e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
       firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }


}
