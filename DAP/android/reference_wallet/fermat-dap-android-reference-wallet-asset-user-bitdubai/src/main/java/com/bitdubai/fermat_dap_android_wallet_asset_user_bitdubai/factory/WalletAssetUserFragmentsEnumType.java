package com.bitdubai.fermat_dap_android_wallet_asset_user_bitdubai.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * WalletAssetIssuerFragmentsEnumType
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public enum WalletAssetUserFragmentsEnumType implements FermatFragmentsEnumType<WalletAssetUserFragmentsEnumType> {


    DAP_WALLET_ASSET_USER_MAIN_ACTIVITY("DWUIMA");

    private String key;

    WalletAssetUserFragmentsEnumType(String key) {
        this.key = key;
    }

    public static WalletAssetUserFragmentsEnumType getValue(String name) {
        for (WalletAssetUserFragmentsEnumType fragments : WalletAssetUserFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }

    @Override
    public String getKey() {
        return key;
    }

}
