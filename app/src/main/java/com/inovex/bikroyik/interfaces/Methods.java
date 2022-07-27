package com.inovex.bikroyik.interfaces;

import com.inovex.bikroyik.model.DueModelData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {

    @GET("api/users?page=2")
    Call<DueModelData> getAllData();
}
