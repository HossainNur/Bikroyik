package com.inovex.bikroyik.UI.activity;

import static com.inovex.bikroyik.AppUtils.Constants.CONTACT_PERMISSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.PhoneBookAdapter;
import com.inovex.bikroyik.data.model.PhoneBookModel;

import java.util.ArrayList;
import java.util.List;

public class PhoneBookActivity extends AppCompatActivity {

    ImageView backButton;
    TextView tv_toolbarText;
    ConstraintLayout no_imageViews;
    RecyclerView contactList_recycler;
    List<PhoneBookModel> phoneBookModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white_5));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_phone_book);


        tv_toolbarText = (TextView) findViewById(R.id.toolbar_title);
        tv_toolbarText.setText(getResources().getString(R.string.phone_book));
        no_imageViews = (ConstraintLayout) findViewById(R.id.no_imageViews);
        contactList_recycler = (RecyclerView) findViewById(R.id.contactList_recycler);

        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int readContact = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);


        //writeContact != PackageManager.PERMISSION_GRANTED ||
        if ( readContact != PackageManager.PERMISSION_GRANTED){
            //Manifest.permission.WRITE_CONTACTS
            ActivityCompat.requestPermissions(
                    PhoneBookActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS}, CONTACT_PERMISSION
            );
        }else {
            getContactList();

            if (phoneBookModelList != null && phoneBookModelList.size() > 0){
                setAdapter(getApplicationContext());

            }else {
                no_imageViews.setVisibility(View.VISIBLE);
            }

        }
    }



    private void setAdapter(Context context){
        PhoneBookAdapter phoneBookAdapter = new PhoneBookAdapter(context, phoneBookModelList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        contactList_recycler.setLayoutManager(layoutManager);
        contactList_recycler.setAdapter(phoneBookAdapter);
    }

    private void getContactList() {
        phoneBookModelList.clear();

        ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);

        if ((cur != null ? cur.getCount() : 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                String id = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME));

                if (cur.getInt(cur.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                        Log.i("_contact_", "Name: " + name);
                        Log.i("_contact_", "Phone Number: " + phoneNo);

                        PhoneBookModel phoneBookModel = new PhoneBookModel(name, phoneNo);
                        phoneBookModelList.add(phoneBookModel);

                    }
                    pCur.close();
                }
            }
        }
        if(cur!=null){
            cur.close();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CONTACT_PERMISSION){
            getContactList();

            if (phoneBookModelList != null && phoneBookModelList.size() > 0){
                setAdapter(getApplicationContext());

            }else {
                no_imageViews.setVisibility(View.VISIBLE);
            }
        }
    }
}