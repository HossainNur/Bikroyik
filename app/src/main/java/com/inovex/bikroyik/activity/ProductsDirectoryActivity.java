package com.inovex.bikroyik.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.BrandAdapter;
import com.inovex.bikroyik.adapter.BrandClass;

import java.util.ArrayList;
import java.util.HashMap;

public class ProductsDirectoryActivity extends AppCompatActivity {

    AppDatabaseHelper appDatabaseHelper;
    ArrayList<HashMap<String,String>> brandsList = new ArrayList<HashMap<String, String>>();
    Context context;
    ArrayList<BrandClass> brandObjectList = new ArrayList<BrandClass>();
    GridView gridView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_directory);
        setTitle(getString(R.string.product_brand_directory));
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        appDatabaseHelper = new AppDatabaseHelper(context);

        brandsList = appDatabaseHelper.getBrandData();
        brandObjectList.clear();


        populateBrandData();
        gridView = findViewById(R.id.brand_grid);
        gridView.setAdapter(new BrandAdapter(context,brandObjectList));


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

    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        super.onBackPressed();
    }
}
