package com.inovex.bikroyik.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.MessageAdapter;
import com.inovex.bikroyik.adapter.Notification;
import com.inovex.bikroyik.model.NoticeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by DELL on 7/31/2018.
 */

public class NoticeFragment extends Fragment {

    private List<Notification> notificationList = new ArrayList<>();
    RecyclerView noticeRecyclerView;
    MessageAdapter notificationAdapter;
    FirebaseDatabase mFireBaseDatabase;
    DatabaseReference globalNoticeDatabaseRef;
    DatabaseReference groupNoticeDatabaseRef;
    DatabaseReference selfNoticeDatabaseRef;

    AppDatabaseHelper appDatabaseHelper;
    ArrayList<NoticeModel> noticeList;
    SessionManager sessionManager;

    public NoticeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getContext());
        appDatabaseHelper = new AppDatabaseHelper(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);

        noticeRecyclerView = (RecyclerView) view.findViewById(R.id.ac_notification_recycler);


        initializeDatabaseReferences();
        // prepareNotificationData();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        noticeList = appDatabaseHelper.getAllMessageDataByType("notice");
        notificationAdapter = new MessageAdapter(noticeList,getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        noticeRecyclerView.setLayoutManager(mLayoutManager);
        noticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noticeRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        noticeRecyclerView.setAdapter(notificationAdapter);

        notificationList.clear();
    }

    private void prepareNotificationData() {
        Notification notification = new Notification("General notice", "Android TextView is one of the powerful and popular UI components which is used to display texts in android application. TextView also allows us to display HTML string. " + "\n" + "So in this tutorial, you will learn to display html string inside android text view and make text of different style like heading, paragraph, lists, text style, text size, text color etc.\n" +
                "\n" +
                "Displaying HTML text in android TextView is very easy. There are many ways to show html string in android textview and in this example," + "\n" + " I will show one of the best and easy ways to display HTML string in android app.\n", "02-08-2018 05:37 PM", false);
        notificationList.add(notification);
        Notification notification2 = new Notification("Job notice", "The undersigned is owner or corporate officer of the owner of ........", "12-07-2018 01:24 PM", false);
        notificationList.add(notification2);
        Notification notification3 = new Notification("Attendance Alert", "Sun Pharma and Ranbaxy Medical Representative will not promote ....", "27-08-2018 12:24 PM", false);
        notificationList.add(notification3);
        Notification notification4 = new Notification("General notice", "If you are changing your job, you are required by law to .......", "09-07-2018 08:54 PM", false);
        notificationList.add(notification4);
        Notification notification5 = new Notification("Holiday notice", "Sun Pharma and Ranbaxy Medical Representative will not promote ....", "13-08-2018 01:24 PM", false);
        notificationList.add(notification5);

        notificationAdapter.notifyDataSetChanged();
    }


    private void initializeDatabaseReferences() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();


        //  String senderEmployeeId = appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_ID);
        //  String selfId = "M" + senderEmployeeId;
        //  Log.v("fire_data", " user id : " + selfId);
        String senderEmployeeId = "9907326110";
        globalNoticeDatabaseRef = FirebaseDatabase.getInstance().getReference().child("All");
        groupNoticeDatabaseRef = FirebaseDatabase.getInstance().getReference().child("SR");

        selfNoticeDatabaseRef = FirebaseDatabase.getInstance().getReference().child(senderEmployeeId);


        selfNoticeDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // messageList.clear();
                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {


                    Log.v("fire_data", "@@@ self childKey: " + chidSnap.getKey());
                    Log.v("fire_data", "@@@ key: " + chidSnap.child("key").getValue());
                    Log.v("fire_data", "@@@ time: " + chidSnap.child("time").getValue());

                    Notification notification = new Notification();
                    notification.setNotificationDate(chidSnap.child("time").getValue().toString());
                    notification.setNotificationText(chidSnap.child("key").getValue().toString());
                    notification.setNotificationType("Individual Notice");
                    notificationList.add(notification);
//                    Log.v("fire_data", "#### sender Name: " + chidSnap.child("sender_name").getValue());
//                    Log.v("fire_data", "#### timestamp: " + chidSnap.child("timestamp").getValue());
//
//                    MessageItem messageItem3 = new MessageItem();
//                    messageItem3.setMessageSenderId(chidSnap.child("sender_id").getValue().toString());
//                    messageItem3.setMessageSender(chidSnap.child("sender_name").getValue().toString());
//
//                    long timestamp = Long.parseLong(chidSnap.child("timestamp").getValue().toString());
//                    String dateString = toDate(timestamp);
//                    String timeString = toTime(timestamp);
//                    messageItem3.setMessageDate(dateString);
//                    messageItem3.setMessageTime(timeString);
//                    messageItem3.setMessageTitle(chidSnap.child("title").getValue().toString());
//                    messageItem3.setMessage(chidSnap.child("message").getValue().toString());
//                    messageList.add(messageItem3);


                }
//                Toast.makeText(mContext, "list: " + messageList.size(), Toast.LENGTH_SHORT).show();
//                messageListAdapter = new MessageListAdapter(messageList, mContext);
//                messageRecyclerView.setAdapter(messageListAdapter);
//                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // progressDialog.dismiss();

            }
        });


        groupNoticeDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                    Log.v("fire_data", "*** group childKey: " + chidSnap.getKey());
                    Log.v("fire_data", "*** key: " + chidSnap.child("key").getValue());
                    Log.v("fire_data", "*** time: " + chidSnap.child("time").getValue());

                    Notification notification = new Notification();
                    notification.setNotificationDate(chidSnap.child("time").getValue().toString());
                    notification.setNotificationText(chidSnap.child("key").getValue().toString());
                    notification.setNotificationType("Group Notice");
                    notificationList.add(notification);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        globalNoticeDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {
                    Log.v("fire_data", "$$$ global childKey: " + chidSnap.getKey());
                    Log.v("fire_data", "$$$ key: " + chidSnap.child("key").getValue());
                    Log.v("fire_data", "$$$ time: " + chidSnap.child("time").getValue());
                    Notification notification = new Notification();
                    notification.setNotificationDate(chidSnap.child("time").getValue().toString());
                    notification.setNotificationText(chidSnap.child("key").getValue().toString());
                    notification.setNotificationType("General Notice");
                    notificationList.add(notification);
                }

                Collections.sort(notificationList, new Comparator<Notification>() {
                    @Override
                    public int compare(Notification l1, Notification l2) {

                        return Long.valueOf(Long.parseLong(l1.getNotificationDate())).compareTo(Long.parseLong(l2.getNotificationDate()));
                    }
                });

                Collections.reverse(notificationList);
                notificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
