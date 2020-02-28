package com.google.adssdktest.mediation;


import com.google.android.gms.ads.reward.RewardItem;

public class TnkPubCustomRewardVideoItem implements RewardItem {
    private final String type;
    private final int amount;

    public TnkPubCustomRewardVideoItem(String type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getAmount() {
        return amount;
    }
}
