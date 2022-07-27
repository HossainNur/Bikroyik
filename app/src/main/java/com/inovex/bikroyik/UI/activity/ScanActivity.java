package com.inovex.bikroyik.UI.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.interfaces.SharedCallback;
import com.inovex.bikroyik.viewmodel.OrderActivityViewModel;

import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ScanActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler{
    private static final int CAMERA_REQUEST_PERMISSION = 176;
    private ZBarScannerView mScannerView;
    OrderActivityViewModel orderActivityViewModel;
    Context mContext;
    private SharedCallback sharedCallback;

    SharedPreference sharedPreference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        mContext = getApplicationContext();
        checkPermission();

        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        String orderId = sharedPreference.getCurrentOrderId();

        if (SharedMethode.getInstance().contextAssigned()) {
            if (SharedMethode.getInstance().getContext() instanceof SharedCallback)
                sharedCallback = (SharedCallback) SharedMethode.getInstance().getContext();

            // to prevent memory leak. no further needs
            SharedMethode.freeContext();
        }




        orderActivityViewModel = new ViewModelProvider(this).get(OrderActivityViewModel.class);
        orderActivityViewModel.init(mContext, sharedPreference.getCurrentOrderId());

        //barcode
        mScannerView = new ZBarScannerView(this);    // Programmatically initialize the scanner view
        setContentView(mScannerView);
    }

    private void checkPermission(){
        // ask for permissions
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_PERMISSION);

        }
    }


    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }
    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }
    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {
        // Do something with the result here
        Log.v("kkkk", result.getContents()); // Prints scan results
        Log.v("uuuu", result.getBarcodeFormat().getName()); // Prints the scan format (qrcode, pdf417 etc.)

        // You can now call your implemented methodes from anywhere at any time
        if (sharedCallback != null)
            Log.d("TAG", "Callback result = " + sharedCallback.getSharedText(result.getContents()));
//        orderActivityViewModel.setBarcodeLiveData(result.getContents());
        onBackPressed();


        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_PERMISSION: {
                onResume();
            }

        }
    }
}