package com.inovex.bikroyik.model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroDueClient {
    private static Retrofit retrofit;
    private static String BASE_URL ="https://reqres.in/";

    public static Retrofit getRetrofitInstance(){
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
