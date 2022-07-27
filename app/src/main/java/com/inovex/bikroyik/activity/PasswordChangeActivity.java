package com.inovex.bikroyik.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.network.volly_method.VolleyMethods;
import com.inovex.bikroyik.data.network.volly_method.interfaces.VolleyCallBack;

import org.json.JSONException;
import org.json.JSONObject;

public class PasswordChangeActivity extends AppCompatActivity {

    ImageView btn_passwordVisibility;
    EditText et_userName, et_password;
    Button btn_savePassword;

    private ProgressDialog progressDialog;
    SessionManager sessionManager;
    Context mContext;
    boolean isHidden = true;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);

        mContext = this;
        sessionManager = new SessionManager(mContext);

        btn_passwordVisibility = (ImageView) findViewById(R.id.iv_btnPasswordVisibility_ChangePassword);
        btn_savePassword = (Button) findViewById(R.id.btnSubmit_passwordChange);
        et_userName = (EditText) findViewById(R.id.username_passwordChange);
        et_password = (EditText) findViewById(R.id.password_passwordChange);

        et_userName.setText(sessionManager.getUserId());


        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("please wait .....");
        progressDialog.setCancelable(false);


        et_userName.setEnabled(false);

        btn_passwordVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isHidden){
                    isHidden = false;
                    //for visible password
                    btn_passwordVisibility.setImageResource(R.drawable.ic_visibility_24);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    et_password.setSelection(position);
                }else {
                    isHidden = true;
                    //for hide password
                    btn_passwordVisibility.setImageResource(R.drawable.ic_visibility_off_24);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    et_password.setSelection(position);
                }

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                position = s.length();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_password.getText().toString().trim();

                if (TextUtils.isEmpty(password) || password.length() < 6){
                    et_password.setError("password should be minimum 6 digits");
                }

                if (!(TextUtils.isEmpty(password)) && password.length() >= 6 && !(TextUtils.isEmpty(sessionManager.getUserId()))){
                    Log.d("_sf_", "userId :"+sessionManager.getUserId()+"password :"+password);

                    if (Constants.isNetworkAvailable(mContext)){
                        progressDialog.show();
                        changePassword(mContext, sessionManager.getUserId(), password);
                    }
                }
            }
        });


    }


    private void changePassword(Context mContext, String userId, String password){

        String url = APIConstants.RESET_PASSWORD_API+"/"+userId+"/"+password;
        VolleyMethods volleyMethods = new VolleyMethods();

        volleyMethods.sendPutRequest(mContext, url, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                Log.d("_sf_", "Password Change API response : "+result);

                progressDialog.dismiss();
                if (result.equalsIgnoreCase("error")){
                    Log.d("error>>", "PasswordChange Volley response gon an error");
                }else {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        long statusCode = jsonObject.getLong("statusCode");


                        if (statusCode == 201){
                            Toast.makeText(mContext, "change successfully", Toast.LENGTH_SHORT);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}