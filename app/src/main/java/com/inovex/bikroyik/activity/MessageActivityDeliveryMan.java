package com.inovex.bikroyik.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.MessageAdapter;
import com.inovex.bikroyik.model.NoticeModel;

import java.util.ArrayList;

public class MessageActivityDeliveryMan extends AppCompatActivity {

    Toolbar mToolbar;
    TextView tvHomeToolbarTitle;
    ImageView ivBackIcon;

    RecyclerView rvMessage;
    MessageAdapter messageAdapter;
    ArrayList<NoticeModel> noticeModelArrayList;

    AppDatabaseHelper appDatabaseHelper;
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_delivery_man);

        sessionManager = new SessionManager(getApplicationContext());
        appDatabaseHelper = new AppDatabaseHelper(getApplicationContext());

        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("Message");
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);


        rvMessage = findViewById(R.id.messageList_delivery_man_recycler);
        rvMessage.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        noticeModelArrayList = appDatabaseHelper.getAllMessageDataByType("message");
        messageAdapter = new MessageAdapter(noticeModelArrayList, getApplicationContext());
        rvMessage.setAdapter(messageAdapter);

        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}