package com.example.tourmate;

public class Expense {
    private String expenseName;
    private double expenseAmount;
    private String expenseDate;
    private String key;
    private String tourName;
    private String tourKey;

    public Expense() {
    }



    public Expense(String expenseName, double expenseAmount, String expenseDate, String key) {
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.key = key;
    }

    public Expense(String expenseName, double expenseAmount, String expenseDate, String key, String tourName, String tourKey) {
        this.expenseName = expenseName;
        this.expenseAmount = expenseAmount;
        this.expenseDate = expenseDate;
        this.key = key;
        this.tourName = tourName;
        this.tourKey = tourKey;
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
        return key;
    }

    public String getKey() {
        return key;
    }

    public String getTourName() {
        return tourName;
    }

    public String getTourKey() {
        return tourKey;
    }
}
