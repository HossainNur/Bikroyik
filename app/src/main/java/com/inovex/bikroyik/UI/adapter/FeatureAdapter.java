package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.ContactListActivity;
import com.inovex.bikroyik.UI.activity.DashBoardActivity;
import com.inovex.bikroyik.activity.ProductsDirectoryActivity;
import com.inovex.bikroyik.data.model.Features;
import com.inovex.bikroyik.interfaces.Callback;
import com.inovex.bikroyik.utils.FeaturesNameConstants;

import java.util.ArrayList;
import java.util.List;

public class FeatureAdapter extends RecyclerView.Adapter<FeatureAdapter.FeatureViewHolder> {
    List<Features> featuresList = new ArrayList<>();
    Context mContext;
    Callback callback;
    boolean canAddHomepageFeature;

    public FeatureAdapter(Context context, List<Features> featuresList, boolean isHomepageStorageAvailable, Callback callback){
        this.featuresList = featuresList;
        this.mContext = context;
        this.callback = callback;
        this.canAddHomepageFeature = isHomepageStorageAvailable;
    }


    @NonNull
    @Override
    public FeatureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feature_item, parent, false);
        return new FeatureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureViewHolder holder, int position) {
        Features feature = featuresList.get(position);
        holder.img_feature.setImageDrawable(
                getDrawable(mContext, feature.getDrawableFileName()));
        holder.tv_tittle.setText(feature.getFeatureName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActivity(feature);
            }
        });

        if (feature.isInHomepage()){
            holder.img_home.setVisibility(View.VISIBLE);
        }else if (!feature.isInHomepage() && canAddHomepageFeature){
            holder.img_home.setImageDrawable(getDrawable(mContext, "add_circle_20"));
            holder.img_home.setVisibility(View.VISIBLE);
        }

        holder.singleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (feature.isInHomepage()){
//                    holder.img_home.setVisibility(View.GONE);
                    callback.callback(feature, false);
                    Log.d("_add_", "remove successfully!");
                }else {
//                    holder.img_home.setVisibility(View.VISIBLE);
//                    feature.setIsInHomepage(true);
                    callback.callback(feature, true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return featuresList.size();
    }


    protected class FeatureViewHolder extends RecyclerView.ViewHolder {
        ImageView img_feature;
        TextView tv_tittle;
        ImageView img_home;
        ConstraintLayout singleItem;

        protected FeatureViewHolder(@NonNull View itemView) {
            super(itemView);
            singleItem = (ConstraintLayout) itemView.findViewById(R.id.consLayout_featureItem);
            img_feature = (ImageView) itemView.findViewById(R.id.img_feature);
            tv_tittle = (TextView) itemView.findViewById(R.id.tv_feature_title);
            img_home = (ImageView) itemView.findViewById(R.id.icon_home_btn);
        }
    }

    private Drawable getDrawable(Context context, String name) {
        int resourceId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return context.getResources().getDrawable(resourceId);
    }

    private void onClickActivity(Features feature){

        switch (feature.getFeatureName()){
            case FeaturesNameConstants.dashboard:
                Intent intent = new Intent(mContext, DashBoardActivity.class);
                mContext.startActivity(intent);
                break;

            case FeaturesNameConstants.sales:
                mContext.startActivity(new Intent(mContext, ProductsDirectoryActivity.class));
                break;

            case FeaturesNameConstants.contact:
                break;

            case FeaturesNameConstants.smsMarketing:
                break;

            case FeaturesNameConstants.expense:
                break;

            case FeaturesNameConstants.productList:
                break;

            case FeaturesNameConstants.due:
                break;

            case FeaturesNameConstants.onlineStore:
                break;

            case FeaturesNameConstants.extraIncome:
                break;

            case FeaturesNameConstants.stock:
                break;

            case FeaturesNameConstants.bikrirhisab:
                break;

        }
    }

}
