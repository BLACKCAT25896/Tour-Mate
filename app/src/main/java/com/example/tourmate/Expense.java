package com.example.tourmate;

public class Expense {
    private String expenseName;
    private double expenseAmount;
    private String expenseDate;

    public Expense() {
    }

    public Expense(String expenseName, double expenseAmount, String expenseDate) {
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public double getExpenseAmount() {
        return expenseAmount;
    }

    public String getExpenseDate() {
        return expenseDate;
    }
}
