package com.example.tourmate;

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

import java.util.ArrayList;
import java.util.List;

public class ExpenseActivity extends AppCompatActivity {
    private ActivityExpenseBinding binding;
    private EditText nameET, amountET, dateET;
    private Button addExpenseBtn;
    private String name, amount, date;
    private List<Expense> expenseList;
    private ExpenseAdapter expenseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_expense);

        init();


        binding.addExpenseFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addExpense();
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


    }
}
