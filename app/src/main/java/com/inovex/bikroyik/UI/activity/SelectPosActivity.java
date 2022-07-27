package com.inovex.bikroyik.UI.activity;

import static com.inovex.bikroyik.activity.MainActivity.MyPREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.adapter.PosAdapter;
import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.Features;
import com.inovex.bikroyik.data.model.LoginResponse;
import com.inovex.bikroyik.data.model.Pos;
import com.inovex.bikroyik.data.model.Store;
import com.inovex.bikroyik.interfaces.CallbackPos;
import com.inovex.bikroyik.utils.Constants;
import com.inovex.bikroyik.utils.FeaturesNameConstants;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class SelectPosActivity extends AppCompatActivity {

    SharedPreference sharedPreference;
    private RecyclerView post_recycler;
    private MaterialSpinner storeSpinner;
    private EditText et_pin;
    private Button btn_submit;
    LoginResponse responseData = null;
    private List<Store> storeList = new ArrayList<>();
    private List<Pos> posList = new ArrayList<>();
    private CallbackPos callbackStore;

    private Store selectedStore = new Store();
    private Pos selectedPos = new Pos();


    private DatabaseSQLite sqLite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);
        sharedPreference = SharedPreference.getInstance(getApplicationContext());
        sqLite = new DatabaseSQLite(getApplicationContext());
        init();

        if (!sharedPreference.getIsLoggedIn()){
            responseData = (LoginResponse) getIntent().getSerializableExtra(Constants.KEY_OBJECT_EXTRA);
            if (responseData != null){
               storeList = responseData.getStores();
               Store store = new Store();
               store.setStoreName("Please select store");
               storeList.add(0, store);

               List<String> storeStringList= new ArrayList<>();
                for (Store s: storeList) {
                    storeStringList.add(s.getStoreName());
                }
               storeSpinnerSetup(getApplicationContext(), storeStringList);
            }
        }else {
            startActivity(new Intent(SelectPosActivity.this, com.inovex.bikroyik.UI.activity.MainActivity.class));
        }

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPos != null){
                    String pin = et_pin.getText().toString();
                    if (!TextUtils.isEmpty(pin) && pin.equals(selectedPos.getPosPin())){
                        sharedPreference.setCurrentPosId(String.valueOf(selectedPos.getPosId()));
                        sharedPreference.setStoreId(String.valueOf(selectedStore.getStoreId()));
                        if (responseData != null){
                            sharedPreference.setSubscriberId(responseData.getSubscriberId());
                            sharedPreference.setIsLoggedIn(true);

                            //for selecting user layout
                            SharedPreferences sharedpreferences;
                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("user", "sr");
                            editor.commit();


                            //go to main activity
                            setupHomeScreen(getApplicationContext());
                            startActivity(new Intent(SelectPosActivity.this, com.inovex.bikroyik.UI.activity.MainActivity.class));
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "please enter valid pin", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "please select a pos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        callbackStore = new CallbackPos() {
            @Override
            public void callback(Pos pos) {
                Log.d("_pos_", "selected pos: "+pos.getPosName());
                selectedPos = pos;
            }
        };
    }

    private void init(){
        post_recycler = (RecyclerView) findViewById(R.id.post_recycler);
        storeSpinner = (MaterialSpinner) findViewById(R.id.spinner_store);
        et_pin = (EditText) findViewById(R.id.et_pin);
        btn_submit = (Button) findViewById(R.id.btn_submit);
    }

    private void storeSpinnerSetup(Context mContext, List<String> storeNameList){

        ArrayAdapter spinner = new ArrayAdapter(mContext, R.layout.spinner_layout, storeNameList);
        spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        storeSpinner.setAdapter(spinner);
        storeSpinner.setDropdownMaxHeight(450);


        storeSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                selectedStorePosList(storeNameList.get(position));
                selectedStore = storeList.get(position);
            }
        });
    }

    private void selectedStorePosList(String storeName){

        if (storeList != null){
            for (Store store: storeList){
                if (store.getStoreName().equals(storeName)){
                    //store found and get pos list
                    sharedPreference.setHelpline(store.getHelpLine());
                    sharedPreference.setStoreContact(store.getContactNumber());
                    sharedPreference.setStoreName(store.getStoreName());
                    sharedPreference.setStoreAddress(store.getStoreAddress());


                    posList = store.getPoses();
                    setPosAdapter(getApplicationContext(), posList);
                    break;
                }
            }
        }
    }



    private void setPosAdapter(Context context, List<Pos> posList){
        PosAdapter posAdapter = PosAdapter.getInstance(context);
        posAdapter.init(context, posList, callbackStore);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 4);
        post_recycler.setLayoutManager(layoutManager);
        post_recycler.setAdapter(posAdapter);
    }

    private void setUserInterface(String userId, String featureName, String featureDrawableName,
                                  boolean isInHomePage, int positionInHomepage){

        sqLite.insertFeatures(userId, featureName, featureDrawableName, isInHomePage, positionInHomepage);
    }

    private void setupHomeScreen(Context context){
        SharedPreference sharedPreference = SharedPreference.getInstance(context);
        String userId = sharedPreference.getUserId();
        List<Features> featuresList = sqLite.getFeatures(userId);
        //viewpage setup
        if (featuresList == null || featuresList.size() <= 0){
            setUserInterface(userId, FeaturesNameConstants.dashboard,
                    "dashboard", true, 1);
            setUserInterface(userId, FeaturesNameConstants.sales,
                    "bechabikri", true, 1);
            setUserInterface(userId, FeaturesNameConstants.bikrirhisab,
                    "bikrirhisab", true, 1);
            setUserInterface(userId, FeaturesNameConstants.stock,
                    "stock", true, 1);
            setUserInterface(userId, FeaturesNameConstants.expense,
                    "khorocher_khata", true, 1);
            setUserInterface(userId, FeaturesNameConstants.due,
                    "bakir_khata", true, 1);
            setUserInterface(userId, FeaturesNameConstants.contact,
                    "jugajug", true, 1);
            setUserInterface(userId, FeaturesNameConstants.productList,
                    "product_list", true, 1);
            setUserInterface(userId, FeaturesNameConstants.extraIncome,
                    "extra_income", false, 1);
            setUserInterface(userId, FeaturesNameConstants.smsMarketing,
                    "smsmarketing", false, 1);
            setUserInterface(userId, FeaturesNameConstants.onlineStore,
                    "onlinestore", false, 1);
        }

    }
}