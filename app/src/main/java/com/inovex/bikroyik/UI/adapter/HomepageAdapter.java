package com.inovex.bikroyik.UI.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.UI.activity.BakirKhata;
import com.inovex.bikroyik.UI.activity.ContactListActivity;
import com.inovex.bikroyik.UI.activity.DashBoardActivity;
import com.inovex.bikroyik.UI.activity.Expense;
import com.inovex.bikroyik.UI.activity.OrderActivity;
import com.inovex.bikroyik.UI.activity.OrderItemActivity;
import com.inovex.bikroyik.UI.activity.ProductListActivity;
import com.inovex.bikroyik.UI.activity.ScreenNavigation;
import com.inovex.bikroyik.UI.activity.StockActivity;
import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.Features;
import com.inovex.bikroyik.utils.FeaturesNameConstants;

import java.util.List;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.HomepageViewHolder> {
    private static HomepageAdapter homepageAdapter =  null;
    private Context context;
    private List<Features> featuresList = null;
    String value;
    private SharedPreference sharedPreference;

    public static HomepageAdapter getInstance(){
        if (homepageAdapter == null){
            homepageAdapter = new HomepageAdapter();
            return  homepageAdapter;
        }
        return homepageAdapter;
    }

    public void init(Context context, List<Features> featuresList){
        this.context = context;
        this.featuresList = featuresList;
    }

    private HomepageAdapter(){}

    @NonNull
    @Override
    public HomepageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.feature_item, parent, false);
        return new HomepageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageViewHolder holder, int position) {
        Features feature = featuresList.get(position);
        holder.img_feature.setImageDrawable(
                getDrawable(context, feature.getDrawableFileName()));

      if(sharedPreference.getLanguage().equals("Bangla"))
      {

          if(feature.getFeatureName().equals("Due"))
          {
              holder.tv_tittle.setText("বাকির খাতা");
          }

          else  if(feature.getFeatureName().equals("Sales History"))
          {
              holder.tv_tittle.setText("বিক্রয় বিস্তারিত ");
          }

          else  if(feature.getFeatureName().equals("Contact"))
          {
              holder.tv_tittle.setText("গ্রাহক তালিকা ");
          }

          else  if(feature.getFeatureName().equals("Dashboard"))
          {
              holder.tv_tittle.setText("ড্যাশবোর্ড");
          }
          else  if(feature.getFeatureName().equals("Expense"))
          {
              holder.tv_tittle.setText("ব্যয়");
          }

          else  if(feature.getFeatureName().equals("Sales"))
          {
              holder.tv_tittle.setText("বিক্রয় ");
          }

          else  if(feature.getFeatureName().equals("Stock"))
          {
              holder.tv_tittle.setText("স্টক ");
          }

          else  if(feature.getFeatureName().equals("Product List"))
          {
              holder.tv_tittle.setText("পণ্য লিস্ট ");
          }

      }

      else{

          holder.tv_tittle.setText(feature.getFeatureName());
     }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActivity(feature, context);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (featuresList != null){
            return featuresList.size();
        }
        return 0;
    }

    private Drawable getDrawable(Context context, String name) {
        int resourceId = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return context.getResources().getDrawable(resourceId);
    }


    protected class HomepageViewHolder extends RecyclerView.ViewHolder{

        ImageView img_feature;
        TextView tv_tittle;
        ImageView img_home;
        ConstraintLayout singleItem;

        public HomepageViewHolder(@NonNull View itemView) {
            super(itemView);
            singleItem = (ConstraintLayout) itemView.findViewById(R.id.consLayout_featureItem);
            img_feature = (ImageView) itemView.findViewById(R.id.img_feature);
            tv_tittle = (TextView) itemView.findViewById(R.id.tv_feature_title);
            img_home = (ImageView) itemView.findViewById(R.id.icon_home_btn);
            sharedPreference = SharedPreference.getInstance(context);
        }
    }

    private void onClickActivity(Features feature, Context mContext){

        switch (feature.getFeatureName()){
            case FeaturesNameConstants.dashboard:
                mContext.startActivity(new Intent(mContext, DashBoardActivity.class));
                break;

            case FeaturesNameConstants.sales:
                mContext.startActivity(new Intent(mContext, OrderActivity.class));
                //mContext.startActivity(new Intent(mContext, OrderItemActivity.class));
                break;

            case FeaturesNameConstants.contact:
                mContext.startActivity(new Intent(mContext, ContactListActivity.class));
                break;

            case FeaturesNameConstants.smsMarketing:
                break;

            case FeaturesNameConstants.expense:
                mContext.startActivity(new Intent(mContext, Expense.class));
                break;

            case FeaturesNameConstants.productList:
                mContext.startActivity(new Intent(mContext, ProductListActivity.class));
                break;

            case FeaturesNameConstants.due:
                mContext.startActivity(new Intent(mContext, BakirKhata.class));
                break;

            case FeaturesNameConstants.onlineStore:
                break;

            case FeaturesNameConstants.extraIncome:
                break;

            case FeaturesNameConstants.stock:
                mContext.startActivity(new Intent(mContext, StockActivity.class));
                break;

            case FeaturesNameConstants.bikrirhisab:
                mContext.startActivity(new Intent(mContext, ScreenNavigation.class));
                break;

        }
    }
}
