package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by francisco on 08/10/15.
 */
public class DigitalAsset {

    private String name;
    private String amount;


    public DigitalAsset(String name, String amount) {
        setName(name);
        setAmount(amount);
    }

    public static ArrayList<DigitalAsset> getAssets() {
        List<DigitalAsset> assets = new ArrayList<>();
        assets.add(new DigitalAsset("KFC Coupon", "150.457"));
        assets.add(new DigitalAsset("Burgerking Coupon", "150.457"));
        assets.add(new DigitalAsset("MacDonalds Coupon", "150.457"));
        assets.add(new DigitalAsset("Free Coupon", "150.457"));
        return (ArrayList<DigitalAsset>) assets;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return String.format("%s | %s BTC", name, amount);
    }
}
