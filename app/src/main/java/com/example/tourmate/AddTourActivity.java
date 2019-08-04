package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate.databinding.ActivityAddTourBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class AddTourActivity extends AppCompatActivity {

    private String name, startLocation, destination, startDate, endDate,budget="0.0";
    private Double Amount=0.0;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ActivityAddTourBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_tour);

        init();
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.tripNameET.getText().toString();
                startLocation = binding.tripStartingLocationET.getText().toString();
                destination = binding.tripDestinationET.getText().toString();
                startDate = binding.tripStartingDateET.getText().toString();
                endDate = binding.tripEndDateET.getText().toString();
                budget = binding.tripBudgetET.getText().toString();
                Amount = Double.parseDouble(budget);


                if (name.isEmpty()) {
                    binding.tripNameET.setError("Please input your name !");
                }if(startLocation.isEmpty()){
                    binding.tripNameET.setError("Please input your Location !");

                }if(destination.isEmpty()){
                    binding.tripDestinationET.setError("Please input Destination !");

                }if(startDate.isEmpty()){
                    binding.tripStartingDateET.setError("Please select Start date !");

                }if(endDate.isEmpty()) {
                    binding.tripEndDateET.setError("Please Select End Date !");

                }if(budget.isEmpty()){
                    binding.tripBudgetET.setError("Please input your Budget For Tour !");

                }

                else {
                    addToDB(name,startLocation,destination,startDate,endDate, Amount);

                }


            }
        });


    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

//    public void addExpense(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View v = inflater.inflate(R.layout.activity_trip_add,null);
//        builder.setView(v);
//        AlertDialog dialog = builder.create();
//        dialog.show();


    private void addToDB(String name, String startLocation, String destination, String startDate, String endDate, Double budget) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        Trip trip = new Trip(name,startLocation,destination,startDate,endDate,budget);
        DatabaseReference tourRef = databaseReference.child("users").child(userId).child("tours");
        tourRef.push().setValue(trip).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddTourActivity.this, "Successfully data Saved to Database", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTourActivity.this,MainActivity.class));

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTourActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
