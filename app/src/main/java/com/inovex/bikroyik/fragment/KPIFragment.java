package com.inovex.bikroyik.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.inovex.bikroyik.AppUtils.AppDatabaseHelper;
import com.inovex.bikroyik.R;
import com.inovex.bikroyik.adapter.Ranking;
import com.inovex.bikroyik.adapter.RankingAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by DELL on 7/31/2018.
 */

public class KPIFragment extends Fragment {

    private List<Ranking> rankingList =new ArrayList<>();
    RecyclerView rankingRecycler;
    RankingAdapter rankingAdapter;
    ArrayList<HashMap<String, String>> gradeDetails = new ArrayList<HashMap<String, String>>();

    public KPIFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_kpi, container, false);

        rankingRecycler = (RecyclerView) view.findViewById(R.id.rank_recycler);
        rankingAdapter = new RankingAdapter(rankingList, getContext());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        rankingRecycler.setLayoutManager(mLayoutManager);
        rankingRecycler.setItemAnimator(new DefaultItemAnimator());
       // rankingRecycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        rankingRecycler.setAdapter(rankingAdapter);

        prepareRankingData();
        return  view;
    }




    private void prepareRankingData() {


        rankingList.clear();

        gradeDetails = new AppDatabaseHelper(getContext()).getGradeData();

        Log.d("workforce_kpi", "prepareRankingData: "+gradeDetails);

        for(int i = 0; i < gradeDetails.size(); i++){
            String name = gradeDetails.get(i).get(AppDatabaseHelper.COLUMN_GRADE_EMPLOYEE_NAME);
            String sales = gradeDetails.get(i).get(AppDatabaseHelper.COLUMN_GRADE_SALES);
            String grade = gradeDetails.get(i).get(AppDatabaseHelper.COLUMN_GRADE);
            String id = gradeDetails.get(i).get(AppDatabaseHelper.COLUMN_GRADE_EMPLOYEE_ID);
            Ranking ranking = new Ranking( name, sales, grade, id);
            rankingList.add(ranking);

        }

        /*Ranking ranking = new Ranking("#05", "Nahidul Islam", "450", "A");
        rankingList.add(ranking);
        Ranking ranking2 = new Ranking("#06", "Mijanur Rahman", "410", "A");
        rankingList.add(ranking2);
        Ranking ranking3 = new Ranking("#07", "Zahid hasan", "350", "A");
        rankingList.add(ranking3);
        Ranking ranking4 = new Ranking("#08", "Monjurul Haq", "320", "A");
        rankingList.add(ranking4);
        Ranking ranking5 = new Ranking("#09", "Ashraful Azam", "310", "A");
        rankingList.add(ranking5);
*/
        rankingAdapter.notifyDataSetChanged();


    }
}
