package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.tourmate.databinding.ActivityUpdateExpenseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class UpdateExpenseActivity extends AppCompatActivity {
    private ActivityUpdateExpenseBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String expName, expAmount, expDate, key;
    private double exp, expenseAmount;
    private String name, date, amount;
    DatePickerDialog datePickerDialog;
    int year;
    int month;
    int dayOfMonth;
    Calendar calendar;
    private String tourName,tourKey;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_update_expense);

        init();
        expName = getIntent().getStringExtra("expName");
        exp = getIntent().getDoubleExtra("expAmount",1);
        expAmount = String.valueOf(exp);
        expDate = getIntent().getStringExtra("expDate");
        key = getIntent().getStringExtra("key");
        tourKey = getIntent().getStringExtra("tourKey");

        binding.nameET.setText(expName);
        binding.amountET.setText(expAmount);
        binding.dateET.setText(expDate);

        binding.dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });

        binding.updateExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UpdateExpenseActivity.this, ""+ key, Toast.LENGTH_SHORT).show();


                name = binding.nameET.getText().toString();
                amount = binding.amountET.getText().toString();
                expenseAmount = Double.parseDouble(amount);
                date = binding.dateET.getText().toString();
                String userId = firebaseAuth.getCurrentUser().getUid();
                Expense expense = new Expense(name, expenseAmount, date,key,tourName,tourKey);

                DatabaseReference tourRef = databaseReference.child("users").child(userId).child("tours").child(tourKey).child("expenses");
                //tourRef.child(key).removeValue();

                tourRef.child(key).setValue(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateExpenseActivity.this, "Successfully Updated to Database", Toast.LENGTH_LONG).show();
                            // Toast.makeText(UpdateTourActivity.this, "" + key + "Testing....", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(UpdateExpenseActivity.this, ExpenseActivity.class));

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateExpenseActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    private void updateExpenseToDB(String name, double expenseAmount, String date) {

        progressDialog.setTitle("Expense updating to database");
        progressDialog.show();
        String userId = firebaseAuth.getCurrentUser().getUid();
        Expense expense = new Expense(name, expenseAmount, date,key,tourName,tourKey);

        DatabaseReference updateExpRef = databaseReference.child("users").child(userId).child("tours").child(tourKey).child("expenses").child(key);

        updateExpRef.setValue(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(UpdateExpenseActivity.this, "SuccessFully Uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateExpenseActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openDatePicker() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(UpdateExpenseActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.dateET.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private void init() {
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
