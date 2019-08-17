package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tourmate.databinding.ActivityAddTourBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTourActivity extends AppCompatActivity {
    private String name, startLocation, destination, startDate, endDate, budget = "0.0";
    private Double Amount = 0.0;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private ActivityAddTourBinding binding;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_tour);

        init();
        binding.tripStartingDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });
        binding.tripEndDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEndDatePicker();
            }
        });
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.tripNameET.getText().toString();
                startLocation = binding.tripStartingLocationET.getText().toString();
                destination = binding.tripDestinationET.getText().toString();
                startDate = binding.tripStartingDateET.getText().toString();
                endDate = binding.tripEndDateET.getText().toString();
                budget = binding.tripBudgetET.getText().toString();

                if (name.isEmpty()) {
                    binding.tripNameET.setError("Please input your name !");
                } else if (startLocation.isEmpty()) {
                    binding.tripNameET.setError("Please input your Location !");

                } else if (destination.isEmpty()) {
                    binding.tripDestinationET.setError("Please input Destination !");

                } else if (startDate.isEmpty()) {
                    binding.tripStartingDateET.setError("Please select Start date !");

                } else if (endDate.isEmpty()) {
                    binding.tripEndDateET.setError("Please Select End Date !");

                } else if (budget.isEmpty()) {
                    binding.tripBudgetET.setError("Please input your Budget For Tour !");

                } else {
                    Amount = Double.parseDouble(budget);
                    addToDB(name, startLocation, destination, startDate, endDate, Amount);

                }


            }
        });
        binding.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    private void openEndDatePicker() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(AddTourActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.tripEndDateET.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    private void openDatePicker() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(AddTourActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.tripStartingDateET.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();

    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void addToDB(String name, String startLocation, String destination, String startDate, String endDate, Double budget) {
        String userId = firebaseAuth.getCurrentUser().getUid();
        String key = databaseReference.push().getKey();
        Trip trip = new Trip(name, startLocation, destination, startDate, endDate, budget, key);
        DatabaseReference tourRef = databaseReference.child("users").child(userId).child("tours");
        tourRef.child(key).setValue(trip).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(AddTourActivity.this, "Successfully data Saved to Database", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(AddTourActivity.this, MainActivity.class));

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTourActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
