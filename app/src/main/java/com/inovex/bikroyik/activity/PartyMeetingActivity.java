package com.inovex.bikroyik.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.inovex.bikroyik.R;
import com.jaredrummler.materialspinner.MaterialSpinner;


public class PartyMeetingActivity extends AppCompatActivity {
    MaterialSpinner spinner;

    public Toolbar mToolbar;
    TextView tvHomeToolbarTitle;
    ImageView ivBackIcon;
    LinearLayout llContactPointInfo;
    Context mContext;
    LinearLayout llBack;
    TextView tvCompleteMeeting;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_meeting);
        mContext = this;

        mToolbar = (Toolbar) findViewById(R.id.tbToolbarMeeting);
        tvHomeToolbarTitle = (TextView) mToolbar.findViewById(R.id.tvHomeToolbarTitle);
        tvHomeToolbarTitle.setText("Party Meeting");
        tvCompleteMeeting=findViewById(R.id.tvCpmpleteMeeting);
        ivBackIcon = mToolbar.findViewById(R.id.ivSyncIcon);
        llBack = mToolbar.findViewById(R.id.llBack);
        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("please wait .....");
        progressDialog.setCancelable(false);

        tvCompleteMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                callTimerThread();

            }
        });



        spinner = (MaterialSpinner) findViewById(R.id.spinner);
        spinner.setDropdownMaxHeight(450);
        spinner.setItems("Select Contact Point ", "Dr. Anowar khan", "Dr. Nafiz Iqbal", "Dr. Shahadath Hossain", "Dr. Mainul Islam");
        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Toast.makeText(getApplicationContext(), "item clicked : " + item, Toast.LENGTH_SHORT).show();
            }
        });


        llContactPointInfo = findViewById(R.id.llContactPointInfo);
        llContactPointInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.contact_point_dialog);
                final Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                // window.setBackgroundDrawableResource(R.color.colorTransparent);
                window.setGravity(Gravity.CENTER);
                dialog.setCancelable(true);

                ImageView ivCancelDialog = dialog.findViewById(R.id.ivCancelDialog);
                TextView ivCallContactPoint = dialog.findViewById(R.id.tvCallContractPoint);
                TextView ivLocationContactPoint = dialog.findViewById(R.id.tvLocationContactPoint);
                ivCancelDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                ivCallContactPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:01812435609"));
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        startActivity(callIntent);
                    }
                });

                ivLocationContactPoint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=BSMMU, Dhaka");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        startActivity(mapIntent);
                    }
                });
                dialog.show();

            }
        });


    }


    private void callTimerThread() {



        final int interval = 2000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                Toast.makeText(getApplicationContext(), "Submitted successfully ", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();

            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);

    }
}
