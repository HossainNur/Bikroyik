package com.inovex.bikroyik.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.model.Features;

import java.util.ArrayList;
import java.util.List;

public class EditPageRepository {
    Context mContext;
    private static EditPageRepository repository;
    DatabaseSQLite databaseSQLite;
    List<Features> datasets = new ArrayList<>();

    public static EditPageRepository getInstance(Context context){
        if (repository == null){
            repository = new EditPageRepository(context);
        }

        return repository;
    }

    private EditPageRepository(Context context){
        mContext = context;
        databaseSQLite = new DatabaseSQLite(context);
    }


    public MutableLiveData<List<Features>> getAllFeature(Context context, String userId){

        if (datasets != null && datasets.size() > 0){
            datasets.clear();
        }
        datasets = databaseSQLite.getFeatures(userId);
        MutableLiveData<List<Features>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(datasets);

        return mutableLiveData;
    }


    public boolean updateFeatures(Context context, Features feature, boolean status){
        return databaseSQLite.updateFeatureContent(feature.getUserId(), feature.getFeatureName(),
                status, feature.getFeaturePosition());
    }



}
