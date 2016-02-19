package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.models;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/18/16.
 */
public class SellInfo {
    private String assetsToSell;
    private String assetValue;
    private int assetValueCurrencyIndex;
    private String totalValue;
    private int totalValueCurrencyIndex;

    public String getAssetsToSell() {
        return assetsToSell;
    }

    public void setAssetsToSell(String assetsToSell) {
        this.assetsToSell = assetsToSell;
    }

    public String getAssetValue() {
        return assetValue;
    }

    public void setAssetValue(String assetValue) {
        this.assetValue = assetValue;
    }

    public int getAssetValueCurrencyIndex() {
        return assetValueCurrencyIndex;
    }

    public void setAssetValueCurrencyIndex(int assetValueCurrencyIndex) {
        this.assetValueCurrencyIndex = assetValueCurrencyIndex;
    }

    public String getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(String totalValue) {
        this.totalValue = totalValue;
    }

    public int getTotalValueCurrencyIndex() {
        return totalValueCurrencyIndex;
    }

    public void setTotalValueCurrencyIndex(int totalValueCurrencyIndex) {
        this.totalValueCurrencyIndex = totalValueCurrencyIndex;
    }
}
