package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.BillModel;
import com.inovex.bikroyik.data.model.SignupModel;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.inovex.bikroyik.utils.ApiConstants;
import com.jaredrummler.materialspinner.MaterialSpinner;

import es.dmoral.toasty.Toasty;

public class SignUpActivity extends AppCompatActivity {

    private TextView name,orgName,store,businessType,toolbar_title,
            packageText,mobile,address,address_id,mobile_id,store_id,organization_id,name_text_id;
    private EditText nameEditText,orgNameEditText,storeEditText,mobileEditText,addressEditText;
    private MaterialSpinner businessSpinner,packageSpinner;
    private ImageView backButton;
    private Button saveButton;
    private String[] business = {"Pharmacy Shop","Restaurants & Cafes","Retail","Book Shop","Mobile Shop",
            "Convenience Stores","Dry Cleaning Services","Franchises","Fruit & Vegetable Shops",
            "Hotels Gaming and Clubs","Quick Service Fast Food"," School Shops"," Service Stations",
            "Clothing store","Theme Parks and Tourist Attractions","Healthcare","Laundry"," Car wash","Landscape and Nursery"};
    private String[] packageType = {"Standard","Advanced","Premium"};
    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);

        ProgressDialog csprogress=new ProgressDialog(SignUpActivity.this);
        sharedPreference = SharedPreference.getInstance(getApplicationContext());

        name = findViewById(R.id.nameText);
        orgName = findViewById(R.id.orgText);
        store = findViewById(R.id.storeText);
        businessType = findViewById(R.id.businessText);
        packageText = findViewById(R.id.packageText);
        mobile = findViewById(R.id.mobileText);
        address = findViewById(R.id.addressText);
        nameEditText = findViewById(R.id.editName);
        orgNameEditText = findViewById(R.id.editOrg);
        storeEditText = findViewById(R.id.editStore);
        mobileEditText = findViewById(R.id.editMobile);
        addressEditText = findViewById(R.id.editAddress);
        businessSpinner = (MaterialSpinner) findViewById(R.id.spinnerBuisnessType);
        packageSpinner = (MaterialSpinner) findViewById(R.id.spinnerPackage);
        saveButton = findViewById(R.id.savebtnId);
        name_text_id = findViewById(R.id.name_text_id);
        organization_id = findViewById(R.id.organization_id);
        store_id = findViewById(R.id.store_id);
        mobile_id = findViewById(R.id.mobile_id);
        address_id = findViewById(R.id.address_id);
        toolbar_title = (TextView) findViewById(R.id.toolbar_title);

        if(sharedPreference.getLanguage().equals("Bangla"))
        {
            toolbar_title.setText("সাইন আপ");
            name.setText("নাম");
            orgName.setText("প্রতিষ্ঠানের নাম");
            store.setText("দোকানের সংখ্যা");
            businessType.setText("ব্যবসায় ধরণ");
            packageText.setText("প্যাকেজের নাম ");
            mobile.setText("মোবাইল");
            address.setText("ঠিকানা ");

            nameEditText.setHint("নাম");
            orgNameEditText.setHint("প্রতিষ্ঠানের নাম");
            storeEditText.setHint("দোকানের সংখ্যা");
            mobileEditText.setHint("মোবাইল");
            addressEditText.setHint("ঠিকানা ");

            name_text_id.setText("নাম পূরন করুন ");
            organization_id.setText("প্রতিষ্ঠানের নাম পূরন করুন ");
            mobile_id.setText("মোবাইল নম্বর পূরন করুন ");
            store_id.setText("দোকানের সংখ্যা পূরন করুন ");
            address_id.setText("ঠিকানা পূরন করুন ");
            saveButton.setText("সেভ করুন");

        }


        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(intent);
            }
        });

        businessSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, business));
        packageSpinner.setAdapter(new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, packageType));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SignupModel signupModel = new SignupModel();
                boolean flag = false;

                if (!TextUtils.isEmpty(nameEditText.getText().toString())){
                    signupModel.setName(nameEditText.getText().toString());
                }else {
                    flag = true;
                    if (name_text_id.getVisibility() == View.GONE){
                        name_text_id.setVisibility(View.VISIBLE);
                    }
                }

                if (!TextUtils.isEmpty(orgNameEditText.getText().toString())){
                    signupModel.setStoreName(orgNameEditText.getText().toString());
                }else {
                    flag = true;
                    if (organization_id.getVisibility() == View.GONE){
                        organization_id.setVisibility(View.VISIBLE);
                    }
                }

                if (!TextUtils.isEmpty(storeEditText.getText().toString())){
                    signupModel.setStoreCount(storeEditText.getText().toString());
                }
                else {
                    flag = true;
                    if (store_id.getVisibility() == View.GONE){
                        store_id.setVisibility(View.VISIBLE);
                    }
                }

                /*String regexStr = "^[0-9]$";
                String number=mobileEditText.getText().toString();
                if(mobileEditText.getText().toString().length()<10 || number.length()>13 || number.matches(regexStr)==false  ) {
                    Toast.makeText(getApplicationContext(),"Please enter valid phone number",Toast.LENGTH_SHORT).show();
                    // am_checked=0;
                }
                */
                String regexStr = "^[0-9]$";
                String number=mobileEditText.getText().toString();

                    if (!TextUtils.isEmpty(mobileEditText.getText().toString())){
                        signupModel.setMobile(mobileEditText.getText().toString());
                    }
                    else {
                        flag = true;
                        if (mobile_id.getVisibility() == View.GONE){
                            mobile_id.setVisibility(View.VISIBLE);
                        }
                    }




                if (!TextUtils.isEmpty(addressEditText.getText().toString())){
                    signupModel.setAddress(addressEditText.getText().toString());
                }
                else {
                    flag = true;
                    if (address_id.getVisibility() == View.GONE){
                        address_id.setVisibility(View.VISIBLE);
                    }
                }

                String text1 = String.valueOf(businessSpinner.getSelectedIndex());
                String text2 = String.valueOf(packageSpinner.getSelectedIndex());

                if (!TextUtils.isEmpty(text1.toString())){
                    signupModel.setBusinessType(text1.toString());
                }else {
                    signupModel.setBusinessType("");
                }
                if (!TextUtils.isEmpty(text2.toString())){
                    signupModel.setPackageName(text2.toString());
                }else {
                    signupModel.setPackageName("");
                }




                if(!flag)
                {
                    csprogress.setMessage("Loading...");
                    csprogress.show();
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            csprogress.dismiss();
                        }
                    }, 1000);
                    sendClientInfoToServer(getApplicationContext(), ApiConstants.SIGN_UP, signupModel);
                }



            }
        });


    }


    private void sendClientInfoToServer(Context context, String url, SignupModel signupModel){
        VolleyMethods volleyMethods = new VolleyMethods();
        String reqBody = prepareJson(signupModel);
        Log.d("_tag_", "json: "+reqBody);

        volleyMethods.sendPostRequestToServer(context, url, prepareJson(signupModel), new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_sign_Up", "response: "+result);

                Toasty.success(SignUpActivity.this, "Your request has been submitted, Our representative Contact will soon!!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, SigninActivity.class);
                startActivity(intent);
                if (!TextUtils.isEmpty(result) && "error".equals(result)){
                    Log.d("_tag_", "response: "+result);
                    Toast toast=Toasty.error(SignUpActivity.this, result.toString(),Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    /*Toast toast=Toast.makeText(BillActivity.this,result.toString(),Toast.LENGTH_SHORT);
                    toast.show();*/
                }

            }
        });
    }

    private String prepareJson(SignupModel signupModel){
        Gson gson = new GsonBuilder().serializeNulls().create();

        return gson.toJson(signupModel);
    }



}