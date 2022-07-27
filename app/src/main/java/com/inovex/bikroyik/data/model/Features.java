package com.inovex.bikroyik.data.model;

public class Features {
    private String userId;
    private String featureName;
    private int position;
    private boolean isInHomepage = false;
    private String drawableFileName = "";


    public Features(){}

    public Features(String userId, String featureName, String drawableFileName,
                    boolean isInHomepage, int position) {
        this.userId = userId;
        this.featureName = featureName;
        this.drawableFileName = drawableFileName;
        this.isInHomepage = isInHomepage;
        this.position = position;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public int getFeaturePosition() {
        return this.position;
    }

    public void setFeaturePosition(int position) {
        this.position = position;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public void setIsInHomepage(boolean isInHomepage){
        this.isInHomepage = isInHomepage;
    }

    public boolean isInHomepage(){
        return this.isInHomepage;
    }

    public String getDrawableFileName() {
        return this.drawableFileName;
    }
    public void setDrawableFileName(String drawableFileName){
        this.drawableFileName = drawableFileName;
    }
}
