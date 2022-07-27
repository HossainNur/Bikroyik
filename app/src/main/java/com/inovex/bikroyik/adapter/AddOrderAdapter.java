package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.inovex.bikroyik.AppUtils.APIConstants;
import com.inovex.bikroyik.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class AddOrderAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private List<String> expandableListTitleCopy;
    private HashMap<String, List<String>> expandableListDetail;
    private HashMap<String, List<String>> expandableListDetailCopy;


    public AddOrderAdapter(Context context, List<String> expandableListTitle, HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);



        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_list_item, null);
        }


        String[] splitString = expandedListText.split("#");
        System.out.println(splitString[0]);
        System.out.println(splitString[1]);

        TextView expandedItemTextOne = (TextView) convertView
                .findViewById(R.id.expandedItemTextOne);
        expandedItemTextOne.setText(splitString[0]);

        TextView expandedItemTextTwo = (TextView) convertView
                .findViewById(R.id.expandedItemTextTwo);
        expandedItemTextTwo.setText(splitString[1]);

        TextView expandedItemTextThree = (TextView) convertView
                .findViewById(R.id.expandedItemTextThree);
        expandedItemTextThree.setText(splitString[2]);

        TextView expandedItemTextFour = (TextView) convertView
                .findViewById(R.id.expandedItemTextFour);
        expandedItemTextFour.setText(splitString[3]);


        //  expandedListTextView.setText(expandedListText);

        ImageView img = convertView.findViewById(R.id.ivProduct);


        String image_name = splitString[4];

        String url = APIConstants.PRODUCT_IMAGE+image_name;

//        Picasso.with(context).load(url).into(img);
        Picasso.get().load(url).into(img);


        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListTitle.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.product_list_group, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(( listTitle).toUpperCase());


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }



}
