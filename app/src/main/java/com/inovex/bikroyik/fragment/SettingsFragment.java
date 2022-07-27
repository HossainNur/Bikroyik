package com.inovex.bikroyik.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.inovex.bikroyik.AppUtils.Constants;
import com.inovex.bikroyik.AppUtils.SessionManager;
import com.inovex.bikroyik.R;


/**
 * Created by DELL on 8/1/2018.
 */



public class SettingsFragment extends Fragment {

    public SettingsFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_settings, container, false);
        onBackPressed(view);
        return  view;
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
