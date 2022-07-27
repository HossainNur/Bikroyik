package com.inovex.bikroyik.UI.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.model.Tax;
import com.inovex.bikroyik.interfaces.TaxCallBack;

import java.util.List;

public class TaxAdapter extends RecyclerView.Adapter<TaxAdapter.TaxViewHolder> {
    private static TaxAdapter adapter = null;
    private Context mContext;
    private List<Tax> taxList;
    private TaxCallBack taxCallBack;

    public static TaxAdapter getInstance(){
        if (adapter == null){
            adapter = new TaxAdapter();
            return adapter;
        }
        return adapter;
    }
    public void init(Context context, List<Tax> taxList, TaxCallBack callBack){
        this.mContext = context;
        this.taxList = taxList;
        this.taxCallBack = callBack;
    }

    @NonNull
    @Override
    public TaxViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.child_tax_discount_view, parent, false);
        return new TaxViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull TaxViewHolder holder, int position) {
        Tax tax = taxList.get(position);

        if (tax != null){
            holder.tv_name.setText(tax.getTaxName());
            holder.tv_value.setText(String.valueOf(tax.getTaxAmount())+"%");

            holder.switch_btn.setChecked(tax.getIsTaxApplied());
            holder.switch_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    taxCallBack.callback(tax, isChecked);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (taxList == null){
            return 0;
        }
        return taxList.size();
    }

    protected class TaxViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name, tv_value;
        Switch switch_btn;

        public TaxViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_value = (TextView) itemView.findViewById(R.id.tv_value);
            switch_btn = (Switch) itemView.findViewById(R.id.switch_btn);
        }
    }
}
