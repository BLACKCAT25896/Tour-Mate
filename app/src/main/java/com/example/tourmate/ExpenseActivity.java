package com.example.tourmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tourmate.databinding.ActivityExpenseBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {
    private ActivityExpenseBinding binding;
    private EditText nameET, amountET, dateET;
    private Button addExpenseBtn;
    private String name, amount, date;
    private List<Expense> expenseList;
    private ExpenseAdapter expenseAdapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expense);

        init();


        getExpense();


        binding.addExpenseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpense();
            }
        });
    }

    private void getExpense() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        final String key = databaseReference.child("users").child(userId).child("tours").push().getKey();
        DatabaseReference expRef = databaseReference.child("users").child(userId).child("tours").child(key).child("expenses");
        expRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    expenseList.clear();

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Expense expense = data.getValue(Expense.class);
                        expenseList.add(expense);
                        expenseAdapter.notifyDataSetChanged();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void addExpense() {

     startActivity(new Intent(ExpenseActivity.this, AddExpenseActivity.class));
    }

    private void init() {
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(expenseList,this);
        binding.expenseRV.setLayoutManager(new LinearLayoutManager(this));
        binding.expenseRV.setAdapter(expenseAdapter);
        firebaseAuth =FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }
}
