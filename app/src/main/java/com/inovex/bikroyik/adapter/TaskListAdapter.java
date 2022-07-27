package com.inovex.bikroyik.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;

import java.util.List;

/**
 * Created by DELL on 8/16/2018.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListViewHolder> {

    private List<Notification> notificationList;
    Context context;


    public TaskListAdapter(List<Notification> notificationList) {
        this.notificationList = notificationList;
//        this.context = context;
    }


    @Override
    public TaskListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_list_row, parent, false);
        context = parent.getContext();

        return new TaskListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TaskListViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Notification notification = notificationList.get(position);
        holder.notificationTitle.setText(notification.getNotificationType());
        holder.notificationDescription.setText(notification.getNotificationText());
        holder.notificationDate.setText(notification.getNotificationDate());
        holder.cbTask.setChecked(notification.isChecked());
        holder.cbTask.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    callTimerThread(holder,position);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TextView notificationTitle, notificationDescription, notificationDate;
        CheckBox cbTask;

        public TaskListViewHolder(View view) {
            super(view);
            notificationTitle = (TextView) view.findViewById(R.id.tvNotificationType);
            notificationDescription = (TextView) view.findViewById(R.id.tvNotificationDescription);
            notificationDate = (TextView) view.findViewById(R.id.tvNotificationDate);
            cbTask=view.findViewById(R.id.cbTask);
        }
    }




    private void callTimerThread(final TaskListViewHolder holder, final int position) {



        AlertDialog.Builder builder = new AlertDialog.Builder(context, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        builder.setMessage(/*Resources.getSystem().getString(R.string.attendance_dialog_subtitle_task)*/"Is this task completed?")
                .setCancelable(false)
                .setPositiveButton(/*Resources.getSystem().getString(R.string.alert_dialog_positive)*/"Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AppDatabaseHelper databaseHelper = new AppDatabaseHelper(context);
                        Notification notification = notificationList.get(position);
                        String taskId = notification.notificationId;

                        if (!TextUtils.isEmpty(taskId)){
                            databaseHelper.deleteSingeTask(taskId);
                        }
                        notificationList.remove(position);
                        TaskListAdapter.this.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                })
                .setNegativeButton(/*Resources.getSystem().getString(R.string.alert_dialog_negative)*/"No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        holder.cbTask.setChecked(false);
                        dialog.cancel();

                    }
                });
        AlertDialog alert = builder.create();
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setIcon(R.drawable.done_24);
        Window window = alert.getWindow();
        window.setGravity(Gravity.CENTER);
        //alert.setTitle(/*Resources.getSystem().getString(R.string.attendance_dialog_title_task)*/"Task Complete!!");
        alert.show();



    }
}

