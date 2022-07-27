package com.inovex.bikroyik.data.imageUpload;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @Multipart
    @POST("client-image")
    Call<ResponseApiModel> uploadImage (
            @Part("type") String type,
            @Part MultipartBody.Part image);

}
