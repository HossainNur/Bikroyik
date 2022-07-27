package com.inovex.bikroyik.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.TargetExpandableListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 12/11/2018.
 */


public class TargetFragment extends Fragment {

    ExpandableListView expandableListView;
    TargetExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    String targetType;
    AppDatabaseHelper appDatabaseHelper;

    ArrayList<HashMap<String, String>> dataListOne;

    public TargetFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_target, container, false);

        appDatabaseHelper = new AppDatabaseHelper(getContext());
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        targetType = getArguments().getString("target_type");
        expandableListTitle = new ArrayList<>();
        expandableListDetail = new HashMap<String, List<String>>();

        dataListOne = new ArrayList<>();
        Bundle bundle = this.getArguments();
        if (bundle.getSerializable("data") != null) {

            dataListOne = (ArrayList<HashMap<String, String>>) bundle.getSerializable("data");
            Log.v("bvp fragment", dataListOne.toString());
        }


        populateDataForExpandableListView(targetType, dataListOne);
        expandableListAdapter = new TargetExpandableListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        if (!expandableListAdapter.isEmpty()) {
            expandableListView.expandGroup(0);
        }

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getContext(), expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition) + "", Toast.LENGTH_SHORT).show();

                // show a dialog and on confirm click show a progress dialog and finish

                return false;
            }
        });


        return view;
    }

    private void populateDataForExpandableListView(String targetType, ArrayList<HashMap<String, String>> dataList) {

        ArrayList<HashMap<String, String>> dataValueList = dataList;
        ArrayList<HashMap<String, String>> dataValueListCopy = dataList;


        if (dataValueList.size() > 0) {

            String previousCategoryName = "";

            for (int x = 0; x < dataValueList.size(); x++) {

                String productCategory = dataList.get(x).get("productCategory");

                if (!productCategory.equalsIgnoreCase(previousCategoryName)) {
                    Log.e("_sf", "route name not previous: " + productCategory);
                    // no day inserted previously with this name
                    previousCategoryName = productCategory;
                    expandableListTitle.add(productCategory);
                    List<String> targetList = new ArrayList<>();
                    for (int y = 0; y < dataValueListCopy.size(); y++) {
                        String productCategoryCopy = dataValueListCopy.get(y).get("productCategory");
                        if (productCategory.equalsIgnoreCase(productCategoryCopy)) {
                            String productName = dataValueListCopy.get(y).get("productName");
                            String total = dataValueListCopy.get(y).get("total");
                            String quantity = dataValueListCopy.get(y).get("quantity");

                            targetList.add( productName + "#" + "Total : " + total + "#" + " Quantity: " + quantity);
                        }
                    }
                    expandableListDetail.put(productCategory, targetList);
                }

            }
        }

        //

    }
}
