package com.inovex.bikroyik.interfaces;

import com.inovex.bikroyik.data.model.EmployeeListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeListApi {

    @GET("posts")
    Call<List<EmployeeListModel>> getmodels();
}
