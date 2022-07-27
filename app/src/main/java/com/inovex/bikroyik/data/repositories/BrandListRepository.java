package com.inovex.bikroyik.data.repositories;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.inovex.bikroyik.data.local.DatabaseSQLite;
import com.inovex.bikroyik.data.model.BrandModel;

import java.util.List;

public class BrandListRepository {
    private static BrandListRepository brandListRepository = null;
    private DatabaseSQLite databaseSQLite;
    private Context mContext;
    private MutableLiveData<List<BrandModel>> allBrandsMDTList = new MutableLiveData<>();

    public static BrandListRepository getInstance(Context mContext){
        if (brandListRepository == null){
            brandListRepository = new BrandListRepository();
            return brandListRepository;
        }

        return brandListRepository;
    }

    public void init(Context context){
        mContext = context;
        databaseSQLite = new DatabaseSQLite(context);
    }

    public MutableLiveData<List<BrandModel>> getAllBrand(Context context){
        List<BrandModel> brandModelArrayList = databaseSQLite.getBrandData();

        allBrandsMDTList.postValue(brandModelArrayList);

        return allBrandsMDTList;
    }

    public void insertBrand(String name, String origin, String logo){
        databaseSQLite.insertBrands(name, origin, logo);
    }

    public void deleteBrandData(Context mContext){
        databaseSQLite.deleteBrandData();
    }
}
