package com.bitdubai.fermat_dap_plugin.layer.wallet.wallet.redeem.point.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.interfaces.AssetRedeemPointWalletList;

/**
 * Created by franklin on 15/10/15.
 */
public class AssetRedeemPointWalletBalance implements AssetRedeemPointWalletList {
    String assetPublicKey;
    String name;
    String description;
    long bookBalance;
    long availableBalance;

    public AssetRedeemPointWalletBalance(String assetPublicKey, String name, String description, long bookBalance, long availableBalance){
        this.assetPublicKey = assetPublicKey;
        this.name = name;
        this.description = description;
        this.bookBalance = bookBalance;
        this.availableBalance = availableBalance;
    };

    public AssetRedeemPointWalletBalance(){};

    public String getAssetPublicKey() {
        return assetPublicKey;
    }
    public void setAssetPublicKey(String assetPublicKey) {
        this.assetPublicKey = assetPublicKey;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public long getBookBalance() {
        return bookBalance;
    }
    public void setBookBalance(long bookBalance) {
        this.bookBalance = bookBalance;
    }

    public long getAvailableBalance() {
        return availableBalance;
    }
    public void setAvailableBalance(long availableBalance) {
        this.availableBalance = availableBalance;
    }
}
