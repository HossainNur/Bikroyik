package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;

import java.util.ArrayList;

public class TargetAdapter extends RecyclerView.Adapter<TargetAdapter.TargetViewHolder> {

    private Context context;
    ArrayList<Target> targetList;

    public TargetAdapter(Context context, ArrayList<Target> targetList) {
        this.context = context;
        this.targetList = targetList;
    }

    @Override
    public TargetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.target_list_row,parent,false);
        context = parent.getContext();
        return new TargetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TargetViewHolder holder, int position) {

        if(position % 2 == 0){
            Log.d("workforce", "onBindViewHolder: "+position);
            holder.linearLayout.setBackgroundColor(context.getColor(R.color.aluminum));
        }

        holder.product.setText(targetList.get(position).getProducts());
        holder.target_qty.setText(targetList.get(position).getTarget_qty());
        holder.target_value.setText(targetList.get(position).getTarget_values()+"/-");
        holder.sale_qty.setText(targetList.get(position).getSale_qty());
        holder.sale_value.setText(targetList.get(position).getSale_vales()+"/-");


    }

    @Override
    public int getItemCount() {
        return targetList.size();

    }


    public class TargetViewHolder extends RecyclerView.ViewHolder{

        TextView product, target_qty, sale_qty, target_value, sale_value;
        LinearLayout linearLayout;

        public TargetViewHolder(View view) {
            super(view);

            product = view.findViewById(R.id.target_product);
            target_qty = view.findViewById(R.id.target_qty);
            sale_qty = view.findViewById(R.id.target_sale_qty);
            target_value = view.findViewById(R.id.target_value);
            sale_value = view.findViewById(R.id.target_sale_value);
            linearLayout = view.findViewById(R.id.ll_target_list_row);



        }
    }

}
