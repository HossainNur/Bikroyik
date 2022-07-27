package com.inovex.bikroyik.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;

import java.util.List;

/**
 * Created by DELL on 11/11/2018.
 */

public class CallSchedulerAdapter extends RecyclerView.Adapter<CallSchedulerAdapter.CallSchedulerListViewHolder> {
    private List<CallScheduler> scheduleList;
    Context mContext;


    public CallSchedulerAdapter(List<CallScheduler> scheduleList, Context nContext) {
        this.scheduleList = scheduleList;
        mContext = nContext;

    }

    public CallSchedulerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public CallSchedulerListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.schedule_list_row, parent, false);

        return new CallSchedulerListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CallSchedulerListViewHolder holder, final int position) {


        final CallScheduler callScheduler = scheduleList.get(position);
        String contactsName = callScheduler.getContactsName();
        String contactsNumber = "Mobile: " + callScheduler.getContactsNumber();
        String dateTime = "Schedule time: " + callScheduler.getScheduleDate() + " " + callScheduler.getSchedulrTime();

        holder.tvscheduleContactsName.setText(contactsName);
        holder.tvscheduleContactsNumber.setText(contactsNumber);
        holder.scheduleDateTime.setText(dateTime);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

                //Uncomment the below code to Set the message and title from the strings.xml file

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to delete this?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                scheduleList.remove(holder.getAdapterPosition());
                                AppDatabaseHelper appDatabaseHelper = new AppDatabaseHelper(mContext);
                                appDatabaseHelper.deleteSingleScheduledCall(callScheduler.getContactsNumber());
                                CallSchedulerAdapter.this.notifyDataSetChanged();
                                dialog.dismiss();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = builder.create();
                alert.setIcon(R.drawable.call_scheduling);
                alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
                Window window = alert.getWindow();
                window.setGravity(Gravity.CENTER);
                //alert.setTitle("Call Scheduling Deletion..");
                alert.show();

                return false;
            }
        });


    }

    @Override
    public int getItemCount() {
        return scheduleList.size();
    }

    public class CallSchedulerListViewHolder extends RecyclerView.ViewHolder {
        public TextView tvscheduleContactsName, tvscheduleContactsNumber, scheduleDateTime, scheduleDone;

        public CallSchedulerListViewHolder(View view) {
            super(view);
            tvscheduleContactsName = (TextView) view.findViewById(R.id.tvScheduleName);
            tvscheduleContactsNumber = (TextView) view.findViewById(R.id.tvScheduleNumber);
            scheduleDateTime = (TextView) view.findViewById(R.id.tvScheduleTime);
            //scheduleDone = (TextView) view.findViewById(R.id.tvExpenseStatus);
        }
    }
}
