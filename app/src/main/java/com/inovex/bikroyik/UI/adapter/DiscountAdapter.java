package com.inovex.bikroyik.UI.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.model.DiscountDetails;
import com.inovex.bikroyik.interfaces.DiscountCallBack;

import java.util.List;

public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder> {
    private static DiscountAdapter adapter = null;
    private Context mContext;
    private List<DiscountDetails> discountList;
    private DiscountCallBack discountCallBack;

    public static DiscountAdapter getInstance(){
        if (adapter == null){
            adapter = new DiscountAdapter();
            return adapter;
        }
        return adapter;
    }

    public void init(Context context, List<DiscountDetails> discountList, DiscountCallBack discountCallBack){
        this.mContext = context;
        this.discountList = discountList;
        this.discountCallBack = discountCallBack;
    }

    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.child_tax_discount_view, parent,false);
        return new DiscountViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
        DiscountDetails discountDetails = discountList.get(position);

        /*if (discountDetails != null){
            holder.tv_name.setText(discountDetails.getDiscountName());
            holder.tv_value.setText(String.valueOf(discountDetails.getDiscount())+"%");

            holder.switch_btn.setChecked(discountDetails.isDiscountApplied());
            holder.switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    discountCallBack.callback(discountDetails, isChecked);
                }
            });
        }*/

        if (!TextUtils.isEmpty(discountDetails.getDiscountName()) &&
                discountDetails.getDiscountName() != null){
            holder.tv_name.setText(discountDetails.getDiscountName());

            if (discountDetails.getDiscountType().equals("BDT")){
                holder.tv_value.setText(String.valueOf(discountDetails.getDiscount()));
            }else {
                holder.tv_value.setText(discountDetails.getDiscount()+"%");
            }

            holder.switch_btn.setChecked(discountDetails.isDiscountApplied());

            holder.switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    discountCallBack.callback(discountDetails, isChecked);
                }
            });
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        if (discountList == null){
            return 0;
        }
        return discountList.size();
    }


    protected class DiscountViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_value;
        Switch switch_btn;
        public DiscountViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_value = (TextView) itemView.findViewById(R.id.tv_value);
            switch_btn = (Switch) itemView.findViewById(R.id.switch_btn);
        }
    }
}
