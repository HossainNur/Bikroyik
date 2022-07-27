package com.inovex.bikroyik.adapter;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppUtil;
import com.inovex.bikroyik.R;

import java.util.List;

/**
 * Created by DELL on 8/2/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;
    Context mContext;
    int noticePosition = 0;


    public NotificationAdapter(List<Notification> notificationList, Context mContext) {
        this.notificationList = notificationList;
        this.mContext = mContext;

    }


    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_row, parent, false);

        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, final int position) {

        final Notification notification = notificationList.get(position);
        holder.notificationTitle.setText(notification.getNotificationType());
        holder.notificationDescription.setText(notification.getNotificationText());
        holder.notificationDate.setText(AppUtil.convertStringToDateTimeString(notification.getNotificationDate()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                noticePosition = position;
                // show a dialog
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.notice_dialog);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

                window.setGravity(Gravity.CENTER);
                dialog.setCancelable(true);

                ImageView ivNextNotice = dialog.findViewById(R.id.ivNextNotice);
                ImageView ivPrevNotice = dialog.findViewById(R.id.ivPrevNotice);
                final TextView tvNoticeText = dialog.findViewById(R.id.tvNoticeText);
                final TextView tvNoticeDateTime = dialog.findViewById(R.id.tvNoticeDateTime);
                final TextView tvNoticeType = dialog.findViewById(R.id.tvNoticeType);

                ivNextNotice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(noticePosition >= notificationList.size() - 1)) {
                            noticePosition++;
                            Notification notification = notificationList.get(noticePosition);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tvNoticeText.setText(Html.fromHtml("<p>" + notification.getNotificationText() + "</p>", Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tvNoticeText.setText(Html.fromHtml("<p>" + notification.getNotificationText() + "</p>"));
                            }
                            tvNoticeDateTime.setText(AppUtil.convertStringToDateTimeString(notification.getNotificationDate()));
                            tvNoticeType.setText(notification.getNotificationType());
                        }


                    }
                });
                ivPrevNotice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (noticePosition > 0) {
                            noticePosition--;
                            Notification notification = notificationList.get(noticePosition);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tvNoticeText.setText(Html.fromHtml("<p>" + notification.getNotificationText() + "</p>", Html.FROM_HTML_MODE_COMPACT));
                            } else {
                                tvNoticeText.setText(Html.fromHtml("<p>" + notification.getNotificationText() + "</p>"));
                            }
                            tvNoticeDateTime.setText(AppUtil.convertStringToDateTimeString(notification.getNotificationDate()));
                            tvNoticeType.setText(notification.getNotificationType());
                        }

                    }
                });

                Notification notification1 = notificationList.get(noticePosition);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvNoticeText.setText(Html.fromHtml("<p>" + notification1.getNotificationText() + "</p>", Html.FROM_HTML_MODE_COMPACT));
                } else {
                    tvNoticeText.setText(Html.fromHtml("<p>" + notification1.getNotificationText() + "</p>"));
                }
                //  tvNoticeText.setText(notification1.getNotificationText());

                tvNoticeDateTime.setText(AppUtil.convertStringToDateTimeString(notification.getNotificationDate()));
                tvNoticeType.setText(notification.getNotificationType());

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView notificationTitle, notificationDescription, notificationDate;

        public NotificationViewHolder(View view) {
            super(view);
            notificationTitle = (TextView) view.findViewById(R.id.tvNotificationType);
            notificationDescription = (TextView) view.findViewById(R.id.tvNotificationDescription);
            notificationDate = (TextView) view.findViewById(R.id.tvNotificationDate);
        }
    }
}
