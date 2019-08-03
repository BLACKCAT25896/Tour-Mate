package com.example.tourmate;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.add_expense_layout,null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

//        nameET = findViewById(R.id.nameET);
//        amountET = findViewById(R.id.amountET);
//        dateET = findViewById(R.id.dateET);
//        addExpenseBtn = findViewById(R.id.addExpenseBTN);
//
//
//        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                name = nameET.getText().toString();
//                amount = amountET.getText().toString();
//                date = dateET.getText().toString();
//
//
//
//
//            }
//        });

        dialog.show();
    }

    private void init() {
        expenseList = new ArrayList<>();
        expenseAdapter = new ExpenseAdapter(expenseList,this);
        binding.expenseRV.setLayoutManager(new LinearLayoutManager(this));
        binding.expenseRV.setAdapter(expenseAdapter);


    }
}
