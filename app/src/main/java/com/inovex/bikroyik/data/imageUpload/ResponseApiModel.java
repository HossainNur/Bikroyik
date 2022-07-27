package com.inovex.bikroyik.data.imageUpload;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseApiModel {
    @SerializedName("images")
    private List<String> images;

    @SerializedName("response")
    private String Response;

    public String getResponse() {
        return Response;
    }
}
