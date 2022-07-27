package com.inovex.bikroyik.viewmodel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.inovex.bikroyik.data.local.SharedPreference;
import com.inovex.bikroyik.data.model.Features;
import com.inovex.bikroyik.data.repositories.EditPageRepository;

import java.util.ArrayList;
import java.util.List;

public class EditHomePageViewModel extends ViewModel {

    private Context mContext;
    private MutableLiveData<List<Features>> mFeatures;
    private MutableLiveData<List<Features>> homeFeatures = new MutableLiveData<>();
    private MutableLiveData<Boolean> isHomepageStorageAvailable = new MutableLiveData<>();
    private MutableLiveData<Boolean> isUpdating = new MutableLiveData<>();
    private List<Features> homeFeaturesList;

    private int totalHomepageFeature;

    private static EditHomePageViewModel instance_;
    EditPageRepository editPageRepository;
    SharedPreference sharedPreference;


    public static EditHomePageViewModel getInstance(){
        if (instance_ == null){
            return new EditHomePageViewModel();
        }
        return instance_;
    }


    public void init(Context context){
        mContext = context;
        sharedPreference = SharedPreference.getInstance(context);
        editPageRepository = EditPageRepository.getInstance(context);

        if (mFeatures != null){
            return;
        }

        if (!TextUtils.isEmpty(sharedPreference.getUserId())){
            isUpdating.setValue(false);
            mFeatures = editPageRepository.getAllFeature(context, sharedPreference.getUserId());
            queryHomepageFeatures();
        }
    }

    public LiveData<List<Features>> getAllFeatures(){
        return mFeatures;
    }

    public void queryHomepageFeatures(){
        if (!TextUtils.isEmpty(sharedPreference.getUserId())){
            isUpdating.setValue(false);
            mFeatures = editPageRepository.getAllFeature(mContext, sharedPreference.getUserId());
        }

        List<Features> featuresList = mFeatures.getValue();
        if (homeFeaturesList == null){
            homeFeaturesList = new ArrayList<>();
        }else {
            homeFeaturesList.clear();
        }

        if (featuresList != null && featuresList.size()>0){
            for (int i=0; i<featuresList.size(); i++){
                if (featuresList.get(i).isInHomepage()){
                    homeFeaturesList.add(featuresList.get(i));
                }
            }
        }

        homeFeatures.setValue(homeFeaturesList);
        totalHomepageFeature = homeFeaturesList.size();
        if (homeFeaturesList.size() < 8){
            isHomepageStorageAvailable.setValue(true);
        }else {
            isHomepageStorageAvailable.setValue(false);
        }

    }

    public LiveData<List<Features>> getHomeFeatures(){
        return homeFeatures;
    }

    private int getFeatureSize(){
        return totalHomepageFeature;
    }

//    private int addItem(){
//        return totalHomepageFeature+1;
//    }
//
//    private int removeItem(){
//        return totalHomepageFeature-1;
//    }


    private void saveUpdate(Context context, Features feature, boolean status){


        new AsyncTask<Features, Void, Boolean>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                isUpdating.setValue(true);
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected Boolean doInBackground(Features... features) {
                boolean isUpdateSuccessful = editPageRepository.updateFeatures(context, features[0], status);

                if (isUpdateSuccessful){
                    features[0].setIsInHomepage(true);
                }

                return isUpdateSuccessful;
            }

            @Override
            protected void onPostExecute(Boolean isSuccess) {
                super.onPostExecute(isSuccess);

                if (isSuccess){
                    List<Features> featuresList = editPageRepository.getAllFeature(context, sharedPreference.getUserId()).getValue();
                    mFeatures.setValue(featuresList);
                    queryHomepageFeatures();
                }


//                try {
//                    Thread.sleep(1500);
//                } catch (InterruptedException e) {
//                    isUpdating.setValue(false);
//                    e.printStackTrace();
//                }
                isUpdating.setValue(false);
            }
        }.execute(feature);

    }

    public void updateFeature(Context context, Features feature, boolean status){
        if (!status && getFeatureSize() > 1){
            saveUpdate(context, feature, status);
        }else if (status && getFeatureSize() < 8){
            saveUpdate(context, feature, status);
        }else if (getFeatureSize() >= 8){
            isHomepageStorageAvailable.setValue(false);
        }
    }

//    public void removeItemFromHomepage(Features feature, int position){
//        List<Features> allFeatures = mFeatures.getValue();
//        if (allFeatures != null && allFeatures.size() >0){
//            allFeatures.remove(position);
//            mFeatures.setValue(allFeatures);
//            getHomepageFeatures();
//        }
//    }

    public LiveData<Boolean> isHomepageStorageAvailable(){
        if (getFeatureSize()<8){
            isHomepageStorageAvailable.setValue(true);
        }else {
            isHomepageStorageAvailable.setValue(false);
        }

        return isHomepageStorageAvailable;
    }

    public LiveData<Boolean> isUpdating(){
        return isUpdating;
    }

}
