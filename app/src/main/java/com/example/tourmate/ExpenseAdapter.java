package com.example.tourmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ViewHolder> {
    private List<Expense> expenseList;
    private Context context;

    public ExpenseAdapter(List<Expense> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_item_layout,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        holder.expenseNameTV.setText(expense.getExpenseName());
        holder.expenseAmountTV.setText(String.valueOf(expense.getExpenseAmount()));
        holder.expenseDateTV.setText(expense.getExpenseDate());


    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView expenseNameTV, expenseAmountTV, expenseDateTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            expenseNameTV = itemView.findViewById(R.id.expenseNameTV);
            expenseAmountTV = itemView.findViewById(R.id.expenseAmountTV);
            expenseDateTV = itemView.findViewById(R.id.expenseDateTV);
        }
    }
}
