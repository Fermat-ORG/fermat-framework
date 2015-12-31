package com.bitdubai.fermat_dap_android_wallet_asset_issuer_bitdubai.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * WalletAssetIssuerFragmentsEnumType
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public enum WalletAssetIssuerFragmentsEnumType implements FermatFragmentsEnumType<WalletAssetIssuerFragmentsEnumType> {


    DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY("DWAIMA"),
    DAP_WALLET_ASSET_ISSUER_ASSET_DETAIL("DWAIAD"),
    DAP_WALLET_ASSET_ISSUER_USER_DELIVERY_LIST("DWAIUDL"),
    DAP_WALLET_ASSET_ISSUER_USER_DELIVERY_LIST_HEADER("DWAIUDLH"),
    DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY("DWAIADL"),
    DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY_TAB_SELECT_USERS("DWAIADTSU");

    private String key;

    WalletAssetIssuerFragmentsEnumType(String key) {
        this.key = key;
    }

    public static WalletAssetIssuerFragmentsEnumType getValue(String name) {
        for (WalletAssetIssuerFragmentsEnumType fragments : WalletAssetIssuerFragmentsEnumType.values()) {
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
