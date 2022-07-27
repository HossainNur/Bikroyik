package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.utils.ApiConstants;

public class OrderDetailsViewActivity extends AppCompatActivity {

    WebView webView;
    String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_view);
        Intent i = getIntent();
        orderId = i.getStringExtra("sendOrderId");

        webView = findViewById(R.id.webViewId);
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(ApiConstants.ORDER_DETAILS +orderId);




    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
        {
            webView.goBack();
            //Toast.makeText(getApplicationContext(),"Going back inside WebView",Toast.LENGTH_SHORT).show();
        }
        else {
            //Toast.makeText(getApplicationContext(),"Exiting a WebView",Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }
}