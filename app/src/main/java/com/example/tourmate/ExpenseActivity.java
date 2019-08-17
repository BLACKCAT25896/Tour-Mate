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
    private List<Expense> expenseList;
    private ExpenseAdapter expenseAdapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private List<String> pushList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expense);

        init();


        getExpense();
        controlFAB();


        binding.addExpenseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpense();
            }
        });
    }
    private void controlFAB() {
        binding.expenseRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && binding.addExpenseFAB.getVisibility() == View.VISIBLE) {
                    binding.addExpenseFAB.hide();
                } else if (dy < 0 && binding.addExpenseFAB.getVisibility() != View.VISIBLE) {
                    binding.addExpenseFAB.show();
                }
            }
        });
    }

    private void getExpense() {
        String userId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference expRef = databaseReference.child("users").child(userId).child("expenses");
        expRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    expenseList.clear();

                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        Expense expense = data.getValue(Expense.class);
                        String pushId = data.getKey();
                        expenseList.add(expense);
                        pushList.add(pushId);
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
        pushList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(expenseList,this);
        binding.expenseRV.setLayoutManager(new LinearLayoutManager(this));
        binding.expenseRV.setAdapter(expenseAdapter);
        firebaseAuth =FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();


    }
}
