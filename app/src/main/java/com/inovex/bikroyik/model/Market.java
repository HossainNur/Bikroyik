package com.inovex.bikroyik.model;

public class Market {
    String marketId, marketName;

    public Market(String marketId, String marketName) {
        this.marketId = marketId;
        this.marketName = marketName;
    }

    public Market(){}

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }
}
