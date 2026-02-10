package com.bank.model;

import java.sql.Timestamp;

public class Transaction {

    private int txId;
    private int accId;
    private String type;
    private double amount;
    private Timestamp txDate;

    // No-arg constructor
    public Transaction() {}

    // Parameterized constructor
    public Transaction(int txId, int accId, String type, double amount, Timestamp txDate) {
        this.txId = txId;
        this.accId = accId;
        this.type = type;
        this.amount = amount;
        this.txDate = txDate;
    }

    // Getters and Setters
    public int getTxId() {
        return txId;
    }

    public void setTxId(int txId) {
        this.txId = txId;
    }

    public int getAccId() {
        return accId;
    }

    public void setAccId(int accId) {
        this.accId = accId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getTxDate() {
        return txDate;
    }

    public void setTxDate(Timestamp txDate) {
        this.txDate = txDate;
    }
}
