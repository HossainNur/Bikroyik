package com.inovex.bikroyik.UI.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.FeatureAdapter;
import com.inovex.bikroyik.data.model.Features;
import com.inovex.bikroyik.interfaces.Callback;
import com.inovex.bikroyik.viewmodel.EditHomePageViewModel;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class EditHomepageActivity extends AppCompatActivity {

    TextView tv_title, tv_description;
    ImageView iv_back;
    ConstraintLayout mToolbar;
    ImageView mToolbarImgView;


    RecyclerView recyclerView;
    ProgressBar progressBar;
    FeatureAdapter mAdapter;

    Callback callback;
    int totalItem;
    boolean flag = false;


    private EditHomePageViewModel mFeatureActivityViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_homepage);


        tv_title = (TextView) findViewById(R.id.tittle_ett);
        tv_description = (TextView) findViewById(R.id.description_ett);
//     iv_back = (ImageView) findViewById(R.id.img_backArrow);

        recyclerView = (RecyclerView) findViewById(R.id.home_page_items);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_ett);

        mToolbar = (ConstraintLayout) findViewById(R.id.toolbar);
        mToolbarImgView = (ImageView) findViewById(R.id.img_backArrow);

        callback = new Callback() {
            @Override
            public void callback(Features feature, boolean status) {
                mFeatureActivityViewModel.updateFeature(getApplicationContext(), feature, status);
            }
        };



        mFeatureActivityViewModel = new ViewModelProvider(this).get(EditHomePageViewModel.class);
        mFeatureActivityViewModel.init(this);



        mFeatureActivityViewModel.isHomepageStorageAvailable().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {

                initRecyclerView(aBoolean);

                if (!aBoolean){
                    if (flag){
                        showToast("Already 8 features added!!");
                    }
                    flag = true;
                }
            }
        });


        mFeatureActivityViewModel.getAllFeatures().observe(this, new Observer<List<Features>>() {
            @Override
            public void onChanged(List<Features> featuresList) {
                mAdapter.notifyDataSetChanged();
            }
        });





//        mFeatureActivityViewModel.isUpdating().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean aBoolean) {
//                if (aBoolean){
//                    showProgressBar();
//                }else {
//                    hideProgressBar();
//                }
//            }
//        }

        mToolbarImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initRecyclerView(boolean isHomepageStorageAvailable){
        mAdapter = new FeatureAdapter(this, mFeatureActivityViewModel.getAllFeatures().getValue()
                ,isHomepageStorageAvailable, callback);
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


    private void showToast(String message){
        Toasty.info(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
    private void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

}