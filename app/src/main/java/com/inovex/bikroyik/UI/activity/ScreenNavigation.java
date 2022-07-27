package com.inovex.bikroyik.UI.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.DueClientAdapter;
import com.inovex.bikroyik.UI.adapter.PagerAdapter;
import com.inovex.bikroyik.UI.adapter.SellsAdapter;
import com.inovex.bikroyik.UI.fragments.DueFragment;
import com.inovex.bikroyik.UI.fragments.PaidFragment;
import com.inovex.bikroyik.UI.fragments.SellsFragment;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.ClientListModel;
import com.inovex.bikroyik.data.model.SellsModel;
import com.inovex.bikroyik.model.DueData;

import java.util.ArrayList;
import java.util.List;

public class ScreenNavigation extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private RecyclerView recyclerView;
    private List<SellsModel> gClientListModel;
    private ArrayList<SellsModel> arrayList = new ArrayList<SellsModel>();
    private SellsAdapter sellsAdapter;
    private ImageView backButton;
    public SharedPreference sharedPreference;
    private TextView toolbar;
    public String[] text2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_screen_navigation);
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        toolbar = findViewById(R.id.toolbar_title);
        tabLayout = findViewById(R.id.tabLayoutId);
        viewPager = findViewById(R.id.viewPagerId);
//        viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        arrayList = new ArrayList<>();
        sellsAdapter = new SellsAdapter(getApplicationContext(), arrayList);
        recyclerView = (RecyclerView) findViewById(R.id.sellsFragment);
        //recyclerView.setAdapter(sellsAdapter);


        if(sharedPreference.getLanguage().equals("Bangla"))
        {
            toolbar.setText("বিক্রয় বিস্তারিত ");
            text2 = new String[]{"সেলস ","পরিশোধিত ","বাকি"};
            viewPager.setAdapter(new PagerAdapter(text2,getSupportFragmentManager()));

        }
        else {
            text2 = new String[]{"sales", "paid", "due"};
            viewPager.setAdapter(new PagerAdapter(text2,getSupportFragmentManager()));
        }
        backButton = (ImageView) findViewById(R.id.btn_imageBack);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    class PagerAdapter extends FragmentPagerAdapter {



        String[] text2;

        public  PagerAdapter(String[] text2,@NonNull FragmentManager fm)
        {

            super(fm);
            this.text2=text2;

        }


        //={"সেলস ","পরিশোধিত ","বাকি "};

        public PagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(position == 0)
            {
                return new SellsFragment();
            }
            else if(position == 1)
            {
                return new PaidFragment();
            }
            else if(position == 2)
            {
                return new DueFragment();
            }



            return null;
        }

        @Override
        public int getCount() {
            return  text2.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return text2[position];
        }
    }





}