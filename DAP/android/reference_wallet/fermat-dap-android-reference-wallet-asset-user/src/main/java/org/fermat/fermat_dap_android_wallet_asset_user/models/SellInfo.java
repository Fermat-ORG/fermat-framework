package org.fermat.fermat_dap_android_wallet_asset_user.models;

import java.io.Serializable;

/**
 * Created by Frank Contreras (contrerasfrank@gmail.com) on 2/18/16.
 */
public class SellInfo implements Serializable {
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
