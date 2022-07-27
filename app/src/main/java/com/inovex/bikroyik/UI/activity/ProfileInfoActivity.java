package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.Store;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProfileInfoActivity extends AppCompatActivity {

    TextView tvProfileName, tvMobileNumber, tvUserAddress,
            tvDistributor, tvWorkingDivision, tvArea, tvReportingPerson, tvReportingContact;
    TextView tvProfileDesignation,toolbar_title,organizationName;
    SharedPreference sharedPreference;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERR_TYPE = "user";
    HashMap<String, String> profileMap;
    private List<Store> storeList = new ArrayList<>();
    ImageView profileImage;
    private ImageView backButton;
    private DatabaseSQLite sqLite;

    AppDatabaseHelper appDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile_info);

        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);
        organizationName = (TextView) findViewById(R.id.organizationName);
        sqLite = new DatabaseSQLite(getApplicationContext());
        tvProfileName = findViewById(R.id.tvProfileName);
        tvMobileNumber = findViewById(R.id.tvMobileNumber);
        tvUserAddress = findViewById(R.id.tvUserAddress);
        tvDistributor = findViewById(R.id.distributorName);
        tvWorkingDivision = findViewById(R.id.working_division);
        tvArea = findViewById(R.id.working_area);
        tvReportingPerson = findViewById(R.id.reporting_person);
        tvReportingContact = findViewById(R.id.reporting_contact);
        profileImage = findViewById(R.id.profileImage);
        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ProfileInfoActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        tvProfileDesignation = findViewById(R.id.tvProfileDesignation);
        tvProfileName.setText(sharedPreference.getStoreName());
        if(sharedPreference.getLanguage().equals("Bangla"))
        {
            toolbar_title.setText("প্রোফাইল");
            organizationName.setText("প্রতিষ্ঠানের নাম");
            tvMobileNumber.setText("মোবাইল:  "+sharedPreference.getStoreContact());
            tvUserAddress.setText("ঠিকানা :  "+sharedPreference.getStoreAddress());
        }
        else{
            tvMobileNumber.setText("Mobile:  "+sharedPreference.getStoreContact());
            tvUserAddress.setText("Address:  "+sharedPreference.getStoreAddress());
        }

    }


}

