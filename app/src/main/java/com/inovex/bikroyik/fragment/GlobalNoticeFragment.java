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
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.Notification;
import com.inovex.bikroyik.adapter.NotificationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 1/3/2019.
 */


public class GlobalNoticeFragment extends Fragment {
    private List<Notification> notificationList = new ArrayList<>();
    RecyclerView noticeRecyclerView;
    NotificationAdapter notificationAdapter;
    FirebaseDatabase mFireBaseDatabase;
    DatabaseReference noticeDatabaseRef;
    AppDatabaseHelper appDatabaseHelper;

    public GlobalNoticeFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_notice, container, false);

        appDatabaseHelper = new AppDatabaseHelper(getContext());
        noticeRecyclerView = (RecyclerView) view.findViewById(R.id.ac_notification_recycler);
        notificationAdapter = new NotificationAdapter(notificationList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        noticeRecyclerView.setLayoutManager(mLayoutManager);
        noticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noticeRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        noticeRecyclerView.setAdapter(notificationAdapter);

        notificationList.clear();
        initializeDatabaseReferences();
        return view;
    }
    private void initializeDatabaseReferences() {
        mFireBaseDatabase = FirebaseDatabase.getInstance();

        String senderEmployeeId = "9907326110";
        noticeDatabaseRef = FirebaseDatabase.getInstance().getReference().child("All");

        noticeDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {


                    Log.v("fire_data", "@@@ self childKey: " + chidSnap.getKey());
                    Log.v("fire_data", "@@@ key: " + chidSnap.child("key").getValue());
                    Log.v("fire_data", "@@@ time: " + chidSnap.child("time").getValue());

                    Notification notification = new Notification();
                    notification.setNotificationDate(chidSnap.child("time").getValue().toString());
                    notification.setNotificationText(chidSnap.child("key").getValue().toString());
                    notification.setNotificationType("Global Notice");
                    notificationList.add(notification);

                }
                notificationAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}

