package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tourmate.databinding.ActivityUpdateTourBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateTourActivity extends AppCompatActivity {
    private ActivityUpdateTourBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String tourName, tourStartingLocation, tourEndLocation, tourStartDate, tourEndDate, budget, key;
    private double tourBudget, budgetAmount;
    private String name, startLocation, destination, startDate, endDate, amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_tour);

        init();


        tourName = getIntent().getStringExtra("tourName");
        tourStartingLocation = getIntent().getStringExtra("tourStartingLocation");
        tourEndLocation = getIntent().getStringExtra("tourDestination");
        tourStartDate = getIntent().getStringExtra("tourStartDate");
        tourEndDate = getIntent().getStringExtra("tourEndDate");
        tourBudget = getIntent().getDoubleExtra("tourBudget", 1);
        budget = String.valueOf(tourBudget);
        key = getIntent().getStringExtra("key");

        binding.tripNameET.setText(tourName);
        binding.tripStartingLocationET.setText(tourStartingLocation);
        binding.tripDestinationET.setText(tourEndLocation);
        binding.tripStartingDateET.setText(tourStartDate);
        binding.tripEndDateET.setText(tourEndDate);
        binding.tripBudgetET.setText(budget);




        binding.updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = binding.tripNameET.getText().toString().trim();
                startLocation = binding.tripStartingLocationET.getText().toString().trim();
                destination = binding.tripStartingDateET.getText().toString().trim();
                startDate = binding.tripStartingDateET.getText().toString().trim();
                endDate = binding.tripEndDateET.getText().toString().trim();
                amount = binding.tripBudgetET.getText().toString().trim();
                budgetAmount = Double.parseDouble(amount);


                String userId = firebaseAuth.getCurrentUser().getUid();
                Trip trip = new Trip(name, startLocation, destination, startDate, endDate, budgetAmount, key);

                DatabaseReference tourRef = databaseReference.child("users").child(userId).child("tours");
                //tourRef.child(key).removeValue();

                tourRef.child(key).setValue(trip).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateTourActivity.this, "Successfully Updated to Database", Toast.LENGTH_LONG).show();
                           // Toast.makeText(UpdateTourActivity.this, "" + key + "Testing....", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UpdateTourActivity.this, MainActivity.class));

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateTourActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateTourActivity.this, MainActivity.class));
            }
        });


    }

    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
