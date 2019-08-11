package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.tourmate.databinding.ActivityAddExpenseBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddExpenseActivity extends AppCompatActivity {
    private ActivityAddExpenseBinding binding;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String name, amount , date;
    private Double expenseAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_add_expense);

        init();
        binding.cancelBtnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        binding.addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = binding.nameET.getText().toString();
                amount = binding.amountET.getText().toString();
                expenseAmount = Double.parseDouble(amount);
                date = binding.dateET.getText().toString();

                addExpenseToDB(name, expenseAmount, date);

                binding.nameET.setText("");
                binding.amountET.setText("");
                binding.dateET.setText("");
                onBackPressed();
            }
        });
    }

    private void addExpenseToDB(String name, Double expenseAmount, String date) {
//        String userId = firebaseAuth.getCurrentUser().getUid();
        final String key = databaseReference.push().getKey();

        Expense expense = new Expense(name, expenseAmount, date,key);

        DatabaseReference tourRef = databaseReference.child("expenses");


        tourRef.push().setValue(expense).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddExpenseActivity.this, "SuccessFully Uploaded", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddExpenseActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}
