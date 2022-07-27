package com.inovex.bikroyik.adapter;

/**
 * Created by HP on 11/10/2018.
 */

public class Expense {

    String expenseType;
    String expenseDate;
    String expenseNote;
    String expenseAmount;
    String expenseApproved;
    String expenseStatus;

    public Expense(String expenseType, String expenseDate, String expenseNote, String expenseAmount, String expenseApproved, String expenseStatus) {

        this.expenseType = expenseType;
        this.expenseDate = expenseDate;
        this.expenseNote = expenseNote;
        this.expenseAmount = expenseAmount;
        this.expenseApproved = expenseApproved;
        this.expenseStatus = expenseStatus;
    }

    public Expense() {
    }

    public String getExpenseType() {
        return expenseType;
    }

    public void setExpenseType(String expenseType) {
        this.expenseType = expenseType;
    }

    public String getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(String expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getExpenseNote() {
        return expenseNote;
    }

    public void setExpenseNote(String expenseNote) {
        this.expenseNote = expenseNote;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public void setExpenseAmount(String expenseAmount) {
        this.expenseAmount = expenseAmount;
    }

    public String getExpenseApproved() {
        return expenseApproved;
    }

    public void setExpenseApproved(String expenseApproved) {
        this.expenseApproved = expenseApproved;
    }

    public String getExpenseStatus() {
        return expenseStatus;
    }

    public void setExpenseStatus(String expenseStatus) {
        this.expenseStatus = expenseStatus;
    }
}
