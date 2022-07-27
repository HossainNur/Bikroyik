package com.inovex.bikroyik.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.model.NoticeModel;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.sql.Timestamp;

public class NotificationActivity extends AppCompatActivity {

    Toolbar mToolbar;
    TextView toolbarText;


    TextView tv_title;
    TextView tv_body;
    TextView tv_date;
    ImageView iv_image;


    AlertDialog.Builder builder;
    AlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        mToolbar = (Toolbar) findViewById(R.id.includeToolbar_NotificationWindrow);
        toolbarText = (TextView) mToolbar.findViewById(R.id.tv_activityTitle);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbarText.setText("Notification");




        init();

        String title = null;
        String body = null;
        String date = null;
        String imageFileLink = null;
        String fileType = "unknown";
        NoticeModel noticeModel = null;
        String notice_id = null;

        if(getIntent().getSerializableExtra("notification") != null){
            noticeModel =(NoticeModel) getIntent().getSerializableExtra("notification");
            notice_id = noticeModel.getNotice_id();
            title = noticeModel.getNotice_title();
            body = noticeModel.getNotice_description();
            date = noticeModel.getDateTime();
            imageFileLink = noticeModel.getFileUrl();
            fileType = noticeModel.getFileType();

            if (!TextUtils.isEmpty(notice_id)){
                Timestamp timestamp = new Timestamp(Long.parseLong(notice_id));
                date = Constants.notificationDateTimeFormate(timestamp);
            }
        }else {
            return;
        }



        if (title.matches("") || title.equals(null) || body.matches("") || body.equals(null) ||
        date.matches("") || date.equals(null)){
            Log.d("_mdm_", "couldn't get data!!");
        }else {
            tv_title.setText(title);
            tv_body.setText(body);
            tv_date.setText(date);
        }
        if (imageFileLink.equals(null) || imageFileLink.matches("")){
           Log.d("_mdm_", "no image reference");
        }else {
            final RequestCreator creator = Picasso.get().load(noticeModel.getFileUrl());
            creator.into(iv_image);
            iv_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog settingsDialog = new Dialog(NotificationActivity.this);
                    settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.message_image_dialog
                            , null));
                    ImageView imageView = settingsDialog.findViewById(R.id.ivMessage);
                    creator.into(imageView);
                    settingsDialog.show();
                }
            });
        }
    }
    private void init(){
        tv_title = findViewById(R.id.tv_notification_win_title);
        tv_date = findViewById(R.id.tv_notification_win_date);
        tv_body = findViewById(R.id.tv_notification_win_body);
        iv_image = findViewById(R.id.iv_notification_win_image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}