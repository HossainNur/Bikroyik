package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.inovex.bikroyik.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 12/19/2018.
 */


public class TargetExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> expandableListTitle;
    private HashMap<String, List<String>> expandableListDetail;

    public TargetExpandableListAdapter(Context context, List<String> expandableListTitle,
                                       HashMap<String, List<String>> expandableListDetail) {
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
            convertView = layoutInflater.inflate(R.layout.target_list_item, null);
        }

        TextView tvTargetProductTitle = (TextView) convertView.findViewById(R.id.tvTargetProductTitle);
        TextView tvTargetProductTotal = convertView.findViewById(R.id.tvTargetProductTotal);
        TextView tvTargetProductQuantity = convertView.findViewById(R.id.tvTargetProductQuantity);

        ImageView ivTargetProduct = convertView.findViewById(R.id.ivTargetProduct);

        String[] splitString = expandedListText.split("#");
//        System.out.println(splitString[0]);
//        System.out.println(splitString[1]+" Tk");
//        System.out.println(splitString[2]+" pcs");



        tvTargetProductTitle.setText(splitString[0]);
        tvTargetProductTotal.setText(splitString[1]+" Tk");
        tvTargetProductQuantity.setText(splitString[2]+" pcs");

        switch (listPosition) {
            case 2:
                if (expandedListPosition == 0) {
                    ivTargetProduct.setImageResource(R.drawable.termeric);
                } else if (expandedListPosition == 1) {
                    ivTargetProduct.setImageResource(R.drawable.chili);
                } else if (expandedListPosition == 2) {
                    ivTargetProduct.setImageResource(R.drawable.coriandar);
                } else if (expandedListPosition == 3) {
                    ivTargetProduct.setImageResource(R.drawable.cumin);
                } else if (expandedListPosition == 4) {
                    ivTargetProduct.setImageResource(R.drawable.chicken);
                }  else {
                    ivTargetProduct.setImageResource(R.drawable.chanachur);
                }
                break;
            case 1:

                if (expandedListPosition == 0) {
                    ivTargetProduct.setImageResource(R.drawable.chanachur);
                } else if (expandedListPosition == 1) {
                    ivTargetProduct.setImageResource(R.drawable.hot_chanachur);
                } else if (expandedListPosition == 2) {
                    ivTargetProduct.setImageResource(R.drawable.bbq);
                } else if (expandedListPosition == 3) {
                    ivTargetProduct.setImageResource(R.drawable.potato);
                } else if (expandedListPosition == 4) {
                    ivTargetProduct.setImageResource(R.drawable.fried_dal);
                }  else {
                    ivTargetProduct.setImageResource(R.drawable.jal_muri);
                }
                break;
            case 0:
                if (expandedListPosition == 0) {
                    ivTargetProduct.setImageResource(R.drawable.mango_pickle);
                } else if (expandedListPosition == 1) {
                    ivTargetProduct.setImageResource(R.drawable.garlic_pickle);
                } else if (expandedListPosition == 2) {
                    ivTargetProduct.setImageResource(R.drawable.mixed_pickle);
                } else if (expandedListPosition == 3) {
                    ivTargetProduct.setImageResource(R.drawable.mango_chatni);
                } else if (expandedListPosition == 4) {
                    ivTargetProduct.setImageResource(R.drawable.boroi_chatni);
                }  else {
                    ivTargetProduct.setImageResource(R.drawable.mango_pickle);
                }
                break;
            case 3:
                if (expandedListPosition == 0) {
                    ivTargetProduct.setImageResource(R.drawable.pineapple_jam);
                } else if (expandedListPosition == 1) {
                    ivTargetProduct.setImageResource(R.drawable.orange_jely);
                } else if (expandedListPosition == 2) {
                    ivTargetProduct.setImageResource(R.drawable.tomato_sauce);
                } else if (expandedListPosition == 3) {
                    ivTargetProduct.setImageResource(R.drawable.tamarind_sauce);
                }   else {
                    ivTargetProduct.setImageResource(R.drawable.tamarind_sauce);
                }
                break;
            case 4:
                if (expandedListPosition == 0) {
                    ivTargetProduct.setImageResource(R.drawable.esopgul);
                } else if (expandedListPosition == 1) {
                    ivTargetProduct.setImageResource(R.drawable.c_mango);
                } else if (expandedListPosition == 2) {
                    ivTargetProduct.setImageResource(R.drawable.lichi);
                } else if (expandedListPosition == 3) {
                    ivTargetProduct.setImageResource(R.drawable.orange_candy);
                } else if (expandedListPosition == 4) {
                    ivTargetProduct.setImageResource(R.drawable.choco);
                }  else {
                    ivTargetProduct.setImageResource(R.drawable.esopgul);
                }
                break;
            case 5:
                if (expandedListPosition == 0) {
                    ivTargetProduct.setImageResource(R.drawable.kalijira);
                } else if (expandedListPosition == 1) {
                    ivTargetProduct.setImageResource(R.drawable.haleem);
                } else if (expandedListPosition == 2) {
                    ivTargetProduct.setImageResource(R.drawable.kheer);
                } else if (expandedListPosition == 3) {
                    ivTargetProduct.setImageResource(R.drawable.toast);
                } else if (expandedListPosition == 4) {
                    ivTargetProduct.setImageResource(R.drawable.dry_cake);
                }  else {
                    ivTargetProduct.setImageResource(R.drawable.kalijira);
                }
                break;
        }



        // set Image
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
            convertView = layoutInflater.inflate(R.layout.target_list_group, null);
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
