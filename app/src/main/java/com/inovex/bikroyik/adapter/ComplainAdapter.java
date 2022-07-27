package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;

import java.util.List;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.ComplainViewHolder>{

    private List<Complain> complainList;
    Context context;

    public ComplainAdapter(List<Complain> complainList, Context context) {
        this.complainList = complainList;
        this.context = context;
    }

    @Override
    public ComplainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_complain_sample, parent, false);
        return new ComplainViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ComplainViewHolder holder, int position) {


        Complain complain = complainList.get(position);

        String name = "Complain From : "+complain.getName();
        String title = "Complain : "+complain.getTitle();
        String notes = complain.getNote();
        Log.d("workforce_complain", "Adapter: "+name+ " "+title+" "+notes);

        holder.name.setText(name);
        holder.title.setText(title);
        holder.notes.setText(notes);



    }

    @Override
    public int getItemCount() {
        return complainList.size();
    }

    public class ComplainViewHolder extends RecyclerView.ViewHolder {
        public TextView name, title, notes;

        public ComplainViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.complain_retailer_name);
            title  = view.findViewById(R.id.complain_title);
            notes = view.findViewById(R.id.complain_details);

        }
    }


}
