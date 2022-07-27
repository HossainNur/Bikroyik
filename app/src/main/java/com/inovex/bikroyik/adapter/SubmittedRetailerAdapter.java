package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;

import java.util.ArrayList;

public class SubmittedRetailerAdapter  extends RecyclerView.Adapter<SubmittedRetailerAdapter.FinalOrderViewHolder>{

    Context context;
    ArrayList<String> retailers = new ArrayList<>();

    public SubmittedRetailerAdapter(Context context, ArrayList<String> retailers) {
        this.context = context;
        this.retailers = retailers;
    }

    @Override
    public FinalOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.submitted_retailer,parent,false);
        context = parent.getContext();
        return new FinalOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FinalOrderViewHolder holder, final int position) {

        final String retailer = retailers.get(position);

        String[] split_str = retailer.split("#");

        holder.name.setText(split_str[1]);
        //holder.type.setText(split_str[0]);
        holder.owner.setText(split_str[2]);
        holder.phone.setText(split_str[3]);
        holder.market.setText(split_str[4]);
        holder.address.setText(split_str[5]);







    }

    @Override
    public int getItemCount() {
        return retailers.size();

    }

    public class FinalOrderViewHolder extends RecyclerView.ViewHolder{

        TextView name, type, owner, address, phone, market;

        public FinalOrderViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.retail_name);
            //type = view.findViewById(R.id.retail_type);
            owner = view.findViewById(R.id.retail_owner);
            address  =view.findViewById(R.id.retail_address);
            phone = view.findViewById(R.id.retail_phone);
            market = view.findViewById(R.id.retail_market);


        }
    }

}

