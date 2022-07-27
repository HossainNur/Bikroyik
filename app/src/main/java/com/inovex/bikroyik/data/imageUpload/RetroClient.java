package com.inovex.bikroyik.data.imageUpload;

import com.inovex.bikroyik.utils.ApiConstants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroClient {
    private static  Retrofit retro = null;
    //private static final String base_url = "https://pos.ahrintl-pvt.com/api/" ;
    private static final String base_url = ApiConstants.IMAGE_BASE_URL;



    private static Retrofit getClient()
    {
        if(retro == null)
        {
            retro  = new Retrofit.Builder()
                     .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return  retro;
    }

    public static ApiServices getApiServices()
    {
        return  getClient().create(ApiServices.class);
    }
}
