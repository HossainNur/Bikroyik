package com.inovex.bikroyik.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

/**
 * Created by DELL on 8/1/2018.
 */

public class ProfileFragment extends Fragment {
    TextView tvProfileName, tvMobileNumber, tvUserAddress, tvDistributor, tvWorkingDivision, tvArea, tvReportingPerson, tvReportingContact;
    TextView tvProfileDesignation;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String USERR_TYPE = "user";
    HashMap<String, String> profileMap;
    ImageView profileImage;

    AppDatabaseHelper appDatabaseHelper;
    String workingArea;


    public ProfileFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        profileMap = new HashMap<>();
        appDatabaseHelper = new AppDatabaseHelper(getContext());
        profileMap = appDatabaseHelper.getEmployeeInfo();
        Log.d("workforce", "onCreateView: "+profileMap);



        tvProfileName = view.findViewById(R.id.tvProfileName);
        tvMobileNumber = view.findViewById(R.id.tvMobileNumber);
        tvUserAddress = view.findViewById(R.id.tvUserAddress);
        tvDistributor = view.findViewById(R.id.distributorName);
        tvWorkingDivision = view.findViewById(R.id.working_division);
        tvArea = view.findViewById(R.id.working_area);
        tvReportingPerson = view.findViewById(R.id.reporting_person);
        tvReportingContact = view.findViewById(R.id.reporting_contact);
        profileImage = view.findViewById(R.id.profileImage);



        tvProfileDesignation = view.findViewById(R.id.tvProfileDesignation);


        if(profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_NAME) != null){
            Log.d("workforce", "onCreateView: "+profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_NAME).toUpperCase());
            tvProfileName.setText(profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_NAME).toUpperCase());
            tvMobileNumber.setText("Mobile: " + profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_PHONE));
            tvUserAddress.setText("Address: " + profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_ADDRESS));
            String employeeCategory = profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_CATEGORY);
            if(employeeCategory.equals("MR")){
                tvProfileDesignation.setText("Designation: Medical representative");
            } else if(employeeCategory.equals("DE")){
                tvProfileDesignation.setText("Designation: Delivery manager");
            }
            else if (employeeCategory.equals("SR")){

                tvProfileDesignation.setText("Designation: Sales Representative");
            }
            tvReportingContact.setText("Reporting contact: "+profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_REPORTING_MOBILE));
            tvReportingPerson.setText("Reporting Person: "+profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_REPORTING_NAME));
            tvArea.setText("Working Area: "+profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_AREA_NAME)+ ", "+profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_REGION_NAME));
            tvDistributor.setText("Distributor Name: "+appDatabaseHelper.getEmployeeInfo().get(AppDatabaseHelper.COLUMN_EMPLOYEE_DISTRIBUTOR_NAME));
            String image_url = APIConstants.PRODUCT_IMAGE+profileMap.get(AppDatabaseHelper.COLUMN_EMPLOYEE_IMAGE);
//            Picasso.with(getContext()).load(image_url).into(profileImage);
            if (!TextUtils.isEmpty(image_url) || image_url != null || image_url != "null"){
                Picasso.get().load(image_url).into(profileImage);
            }

        }

        onBackPressed(view);
        return view;
    }


    private void onBackPressed(View view){

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("_back_", "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.i("_back_", "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);


                    SessionManager sessionManager = new SessionManager(getContext());
                    Fragment fragment = null;
                    if (sessionManager.getEmployeeCategory().equals("SR")){
                        fragment = new HomeFragmentSR();

                    }else if (sessionManager.getEmployeeCategory().equalsIgnoreCase("DE")){
                        fragment = new HomeFragmentSR();
                    }
                    Constants.moveHomeFragment(fragment, getActivity(), getContext());
                    return true;
                }
                return false;
            }
        });
    }


}
