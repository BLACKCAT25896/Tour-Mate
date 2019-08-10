package com.example.tourmate;

public class Expense {
    private String expenseName;
    private double expenseAmount;
    private String expenseDate;
    private String id;

    public Expense() {
    }



    public Expense(String expenseName, double expenseAmount, String expenseDate, String id) {
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.id = id;
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

    public String getId() {
        return id;
    }
}
