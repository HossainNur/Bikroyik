package com.inovex.bikroyik.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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

public class NoticeActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TextView tvHomeToolbarTitle;
    ImageView ivBackIcon;

    Context mContext;

    AppDatabaseHelper appDatabaseHelper;
    ArrayList<NoticeModel> noticeList;

    MessageAdapter notificationAdapter;
    RecyclerView noticeRecyclerView;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);

        mContext = this;

        appDatabaseHelper = new AppDatabaseHelper(mContext);
        sessionManager = new SessionManager(mContext);


        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("Notice");
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);


        noticeRecyclerView = (RecyclerView) findViewById(R.id.recycler_noticeActivity);


        ivBackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NoticeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        noticeList = appDatabaseHelper.getAllMessageDataByType("notice");
        notificationAdapter = new MessageAdapter(noticeList,mContext);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        noticeRecyclerView.setLayoutManager(mLayoutManager);
        noticeRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noticeRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        noticeRecyclerView.setAdapter(notificationAdapter);


    }
}