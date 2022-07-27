package com.inovex.bikroyik.model;

public class DuePaymentWay {
    Double cash,card,mobile_bank;

    public DuePaymentWay(Double cash, Double card, Double mobile_bank) {
        this.cash = cash;
        this.card = card;
        this.mobile_bank = mobile_bank;
    }


    public Double getCash() {
        return cash;
    }

    public void setCash(Double cash) {
        this.cash = cash;
    }

    public Double getCard() {
        return card;
    }

    public void setCard(Double card) {
        this.card = card;
    }

    public Double getMobile_bank() {
        return mobile_bank;
    }

    public void setMobile_bank(Double mobile_bank) {
        this.mobile_bank = mobile_bank;
    }
}
