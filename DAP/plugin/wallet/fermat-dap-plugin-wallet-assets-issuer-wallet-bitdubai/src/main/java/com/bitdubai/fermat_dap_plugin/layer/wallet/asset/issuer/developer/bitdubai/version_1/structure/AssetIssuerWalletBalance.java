package com.bitdubai.fermat_dap_plugin.layer.wallet.asset.issuer.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletList;

/**
 * Created by franklin on 29/09/15.
 */
public class AssetIssuerWalletBalance implements AssetIssuerWalletList {
    String assetPublicKey;
    String name;
    String description;
    long bookBalance;
    long availableBalance;
    long quantityBookBalance;
    long quantityAvailableBalance;

    public AssetIssuerWalletBalance(String assetPublicKey, String name, String description, long bookBalance, long availableBalance, long quantityBookBalance, long quantityAvailableBalance){
        this.assetPublicKey = assetPublicKey;
        this.name = name;
        this.description = description;
        this.bookBalance = bookBalance;
        this.availableBalance = availableBalance;
        this.quantityAvailableBalance = quantityAvailableBalance;
        this.quantityBookBalance = quantityBookBalance;
    };

    public AssetIssuerWalletBalance(){};

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

    @Override
    public long getQuantityBookBalance() {
        return quantityBookBalance;
    }

    @Override
    public void setQuantityBookBalance(long quantityBookBalance) {
        this.quantityBookBalance = quantityBookBalance;
    }

    @Override
    public long getQuantityAvailableBalance() {
        return quantityAvailableBalance;
    }

    @Override
    public void setQuantityAvailableBalance(long quantityAvailableBalance) {
        this.quantityAvailableBalance = quantityAvailableBalance;
    }

}
