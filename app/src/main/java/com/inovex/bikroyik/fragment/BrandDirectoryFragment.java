package com.inovex.bikroyik.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.BrandAdapterFragment;
import com.inovex.bikroyik.adapter.BrandClass;

import java.util.ArrayList;
import java.util.HashMap;


public class BrandDirectoryFragment extends Fragment {

    AppDatabaseHelper appDatabaseHelper;
    ArrayList<HashMap<String,String>> brandsList = new ArrayList<HashMap<String, String>>();
    Context context;
    ArrayList<BrandClass> brandObjectList = new ArrayList<BrandClass>();
    GridView gridView;


    public BrandDirectoryFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_brand_directory, container, false);

        onBackPressed(view);
        context = getContext();

        appDatabaseHelper = new AppDatabaseHelper(context);

        brandsList = appDatabaseHelper.getBrandData();
        brandObjectList.clear();


        populateBrandData();
        gridView = view.findViewById(R.id.brand_grid);

        Log.d("workforce_product", "onCreateView: "+brandObjectList+" "+gridView);

        BrandAdapterFragment brandAdapter = new BrandAdapterFragment(getContext(),brandObjectList);

        gridView.setAdapter(brandAdapter);


        // Inflate the layout for this fragment
        return view;
    }

    private void populateBrandData() {

        for (int i = 0; i < brandsList.size(); i++){

            String name =  brandsList.get(i).get(AppDatabaseHelper.COLUMN_BRAND_NAME);
            String origin = brandsList.get(i).get(AppDatabaseHelper.COLUMN_BRAND_ORIGIN);
            String images = brandsList.get(i).get(AppDatabaseHelper.COLUMN_BRAND_LOGO);

            BrandClass brandClass = new BrandClass(name,origin,images);
            brandObjectList.add(brandClass);

        }
        BrandClass brandClass = new BrandClass("name","origin","");
        brandObjectList.add(brandClass);

        Log.d("workforce_brand", "populateBrandData: "+brandObjectList.size());

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