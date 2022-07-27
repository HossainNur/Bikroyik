package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;

import java.util.List;

public class LeaveAdapter extends RecyclerView.Adapter<LeaveAdapter.LeaveViewHolder> {

    private List<Leaves> leavesList;
    Context context;

    public LeaveAdapter(List<Leaves> leavesList, Context context) {
        this.leavesList = leavesList;
        this.context = context;
    }

    @Override
    public LeaveViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.leave_sample, parent, false);

        return new LeaveViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LeaveViewHolder holder, final int position) {

        final Leaves leaves = leavesList.get(position);
        String type = "Type : " + leaves.getType();
        holder.leavesType.setText(type);
        String from = "From : " + leaves.getFrom();
        holder.leavesFrom.setText(from);
        holder.leavesNote.setText(leaves.getNotes());
        final String to = "To : " + leaves.getTo();
        holder.leavesTo.setText(to);
        String status = leaves.getStatus();
        holder.leavesStatus.setText(status);


    }

    @Override
    public int getItemCount() {
        return leavesList.size();
    }

    public class LeaveViewHolder extends RecyclerView.ViewHolder {
        public TextView leavesType, leavesStatus, leavesFrom, leavesTo,leavesNote;

        public LeaveViewHolder(View view) {
            super(view);
            leavesType = (TextView) view.findViewById(R.id.sample_leave_type);
            leavesStatus = (TextView) view.findViewById(R.id.sample_leave_status);
            leavesFrom = (TextView) view.findViewById(R.id.sample_leave_from);
            leavesTo = (TextView) view.findViewById(R.id.sample_leave_to);
            leavesNote = (TextView) view.findViewById(R.id.sample_leave_note);
        }
    }





}

