package com.bank.model;

public class Account {

    private int accId;
    private int userId;
    private double balance;

    // Constructors
    public Account() {}

    public Account(int accId, int userId, double balance) {
        this.accId = accId;
        this.userId = userId;
        this.balance = balance;
    }

    // Getters & Setters
    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}

