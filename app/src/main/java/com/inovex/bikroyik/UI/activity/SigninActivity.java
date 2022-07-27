package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.LoginResponse;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;
import com.inovex.bikroyik.utils.ApiConstants;
import com.inovex.bikroyik.utils.Constants;
import com.inovex.bikroyik.utils.FeaturesNameConstants;

import org.json.JSONException;
import org.json.JSONObject;

import es.dmoral.toasty.Toasty;

public class SigninActivity extends AppCompatActivity {
    EditText et_phone, et_password;
    Button btn_submit,signupButton;
    TextView textLoginPage,forgotText;

    Context mContext;
    VolleyMethods volleyMethods;
    private SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(R.layout.activity_signin);
        mContext = getApplicationContext();
        init();
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        volleyMethods = new VolleyMethods();

        if (sharedPreference.getLanguage() == null) {
            sharedPreference.setLanguage("English");
        }

        if(sharedPreference.getLanguage().equals("Bangla"))
        {
            textLoginPage.setText("সর্বাধিক ফিচারে ব্যবসার সেরা অ্যাপ");
            et_phone.setHint("ইমেইল/মোবাইল");
            et_password.setHint("পাসওয়ার্ড");
            btn_submit.setText("সাইন ইন");
            signupButton.setText("সাইন আপ");
            forgotText.setText("পাসওয়ার্ড ভুলে গেছেন");
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isNetworkAvailable())
                {
                    if (!TextUtils.isEmpty(et_phone.getText().toString()) && !TextUtils.isEmpty(et_password.getText().toString())){
                        sharedPreference.setUserId(et_phone.getText().toString());


                        String body = requestBody(et_phone.getText().toString(), et_password.getText().toString());
                        String url = ApiConstants.LOGIN_URL;
                        Log.d("_signin_", "onClick: "+url);

                        sendLoginRequestToServer(mContext, url, body);

                    }

                }else{
                    Toasty.warning(getApplicationContext(),"Please Check internet connection!!",Toasty.LENGTH_SHORT).show();
                }

            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void init(){
        et_phone = (EditText) findViewById(R.id.et_phone_number);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        textLoginPage = (TextView) findViewById(R.id.textLoginPage);
        signupButton = (Button) findViewById(R.id.signupButton);
        forgotText = (TextView) findViewById(R.id.textView3);

    }

    private String requestBody(String phone, String password){
        String jsonBody = null;
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", phone);
            jsonObject.put("password", password);

            jsonBody = jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonBody;
    }

    private void sendLoginRequestToServer(Context context, String url, String jsonBody){
        Log.d("_signin_", ""+jsonBody);
        volleyMethods.sendPostRequestToServer(context, url, jsonBody, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_signin_", ""+result);

                if (!TextUtils.isEmpty(result) && !result.equals("error")){
                    Gson gson = new Gson();
                    LoginResponse response = (LoginResponse) gson.fromJson(result, LoginResponse.class);

                    if (response != null && (response.getCode() == 200 || response.getCode() == 201)){
                        //set home screen feature


                        Intent intent = new Intent(SigninActivity.this, SelectPosActivity.class);
                        intent.putExtra(Constants.KEY_OBJECT_EXTRA, response);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(),
                                "username or password is incorrect", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }

    public void onBackPressed(){
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }


}