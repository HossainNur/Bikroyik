package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;

import java.util.List;


public class ExpenseListAdapter extends RecyclerView.Adapter<ExpenseListAdapter.ExpenseListViewHolder> {

    private List<Expense> expenseList;
    Context mContext;


    public ExpenseListAdapter(List<Expense> expenseList, Context nContext) {
        this.expenseList = expenseList;
        mContext = nContext;

    }


    @Override
    public ExpenseListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_row, parent, false);

        return new ExpenseListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpenseListViewHolder holder, final int position) {

        holder.sample.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_transition));


        final Expense expense = expenseList.get(position);
        String expenseTypeString = "Type : " + expense.getExpenseType();
        holder.expenseType.setText(expenseTypeString);
        String dateTime = "Date : " + expense.getExpenseDate();
        holder.expenseDateTime.setText(dateTime);
        holder.expenseNote.setText(expense.getExpenseNote());
        final String expenseAmount = "Amount : " + expense.getExpenseAmount() + " Tk";
        holder.expenseAmount.setText(expenseAmount);
        String expenseStatus = expense.getExpenseStatus();
        String approvedMoney = "Approved Money : "+ expense.getExpenseApproved();
        holder.expenseApproval.setText(approvedMoney);
        holder.expenseSendingStatus.setText(expenseStatus);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ExpenseListViewHolder extends RecyclerView.ViewHolder {
        public TextView expenseType, expenseDateTime, expenseNote, expenseAmount, expenseSendingStatus, expenseApproval;
        LinearLayout sample;

        public ExpenseListViewHolder(View view) {
            super(view);
            expenseType = (TextView) view.findViewById(R.id.tvExpenseType);
            expenseDateTime = (TextView) view.findViewById(R.id.tvExpenseDateTime);
            expenseNote = (TextView) view.findViewById(R.id.tvExpenseNote);
            expenseAmount = (TextView) view.findViewById(R.id.tvExpenseAmount);
            expenseSendingStatus = (TextView) view.findViewById(R.id.tvExpenseStatus);
            expenseApproval = view.findViewById(R.id.tvExpenseAmountApproved);
            sample = view.findViewById(R.id.expenseLinearLayout);
        }
    }





}

