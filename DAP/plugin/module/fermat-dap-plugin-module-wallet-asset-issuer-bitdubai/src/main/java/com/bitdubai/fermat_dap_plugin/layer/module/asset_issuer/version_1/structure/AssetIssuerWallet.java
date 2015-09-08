package com.bitdubai.fermat_dap_plugin.layer.module.asset_issuer.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletType;
import com.bitdubai.fermat_dap_api.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_issuer_wallet.interfaces.AssetIssuerWalletBalance;
import com.bitdubai.fermat_dap_api.layer.module.asset_issuer.interfaces.AssetIssuer;

/**
 * Created by franklin on 07/09/15.
 */
public class AssetIssuerWallet extends DigitalAsset implements AssetIssuer {
    String assetUserIdentityPublicKey;
    WalletCategory walletCategory;
    WalletType walletType;


    @Override
    public String getAssetUserIdentityPublicKey() {
        return this.assetUserIdentityPublicKey;
    }

    @Override
    public void setAssetUserIdentityPublicKey(String assetUserIdentityPublicKey) {
        this.assetUserIdentityPublicKey = assetUserIdentityPublicKey;
    }

    @Override
    public WalletCategory getWalletCategory() {
        return this.walletCategory;
    }

    @Override
    public void setWalletCategory(WalletCategory walletCategory) {
        this.walletCategory = walletCategory;
    }

    @Override
    public WalletType getWalletType() {
        return this.walletType;
    }

    @Override
    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }
}
