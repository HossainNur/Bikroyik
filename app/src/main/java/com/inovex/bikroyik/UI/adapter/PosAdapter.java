package com.inovex.bikroyik.UI.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.data.model.Pos;
import com.inovex.bikroyik.interfaces.CallbackPos;

import java.util.ArrayList;
import java.util.List;

public class PosAdapter extends RecyclerView.Adapter<PosAdapter.PosViewHolder> {
    Context mContext;
    List<Pos> posList = new ArrayList<>();
    private static PosAdapter posAdapter = null;
    private int row_index = -1;
    private CallbackPos callbackPos;

    public static PosAdapter getInstance(Context context){
        if (posAdapter == null){
            posAdapter = new PosAdapter();
            return posAdapter;
        }
        return posAdapter;
    }

    public void init(Context context, List<Pos>posList, CallbackPos callbackPos){
        this.mContext = context;
        this.posList = posList;
        this.callbackPos = callbackPos;
        row_index = -1;
    }

    @NonNull
    @Override
    public PosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.child_pos, parent, false);
        return new PosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PosViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Pos pos = posList.get(position);
        if (!TextUtils.isEmpty(pos.getPosName())){
            holder.tv_posName.setText(pos.getPosName());
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index =position;

                callbackPos.callback(posList.get(position));
                notifyDataSetChanged();
            }
        });


        if(row_index==position){
            holder.parent_view.setBackgroundColor(Color.parseColor("#567845"));
        }
        else
        {
            holder.parent_view.setBackgroundColor(Color.parseColor("#EEEEEE"));
        }

    }

    @Override
    public int getItemCount() {
        if (posList != null && posList.size() > 0){
            return posList.size();
        }
        return 0;
    }

    protected class PosViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_posId, tv_posName;
        private ConstraintLayout parent_view;
        public PosViewHolder(@NonNull View itemView) {
            super(itemView);
//            tv_posId = (TextView) itemView.findViewById(R.id.tv_posId);
            parent_view = (ConstraintLayout) itemView.findViewById(R.id.parent_view);
            tv_posName = (TextView) itemView.findViewById(R.id.tv_posName);
        }
    }
}
