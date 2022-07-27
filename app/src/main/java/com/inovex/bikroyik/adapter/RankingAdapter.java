package com.inovex.bikroyik.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.R;
import com.inovex.bikroyik.activity.MainActivity;

import java.util.List;

/**
 * Created by DELL on 8/5/2018.
 */



public class RankingAdapter extends RecyclerView.Adapter<RankingAdapter.RankingViewHolder> {

    private List<Ranking> rankingList;
    private Context context;

    public RankingAdapter(List<Ranking> rankingList, Context context) {
        this.rankingList = rankingList;
        this.context = context;
    }

    @Override
    public RankingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ranking_list_row, parent, false);

        return new RankingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RankingViewHolder holder, int position) {
        Ranking ranking = rankingList.get(position);
        holder.rankNumber.setText(ranking.getGrade());
        holder.rankName.setText(ranking.getRankName());
        holder.rankPoint.setText("Sale Values : "+ranking.getRankPoint());

        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(MainActivity.USER_ID, null);

        Log.d("workforce_kpi", "onBindViewHolder: "+id + " "+ranking.getId() );

        if (id.equalsIgnoreCase(ranking.getId())){
            holder.rankNumber.setTextColor(Color.parseColor("#E43F3F"));
            holder.rankName.setTextColor(Color.parseColor("#E43F3F"));
            holder.rankPoint.setTextColor(Color.parseColor("#E43F3F"));
        }
    }

    @Override
    public int getItemCount() {
        return rankingList.size();
    }

    public class RankingViewHolder extends RecyclerView.ViewHolder {
        public TextView rankNumber, rankName, rankPoint;

        public RankingViewHolder(View view) {
            super(view);
            rankNumber = (TextView) view.findViewById(R.id.tvRankNumber);
            rankName = (TextView) view.findViewById(R.id.tvRankName);
            rankPoint = (TextView) view.findViewById(R.id.tvRankPoint);

        }
    }
}

