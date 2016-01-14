package com.bitdubai.fermat_dap_android_wallet_redeem_point_bitdubai.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * WalletAssetIssuerFragmentsEnumType
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public enum WalletRedeemPointFragmentsEnumType implements FermatFragmentsEnumType<WalletRedeemPointFragmentsEnumType> {


    DAP_WALLET_REDEEM_POINT_MAIN_ACTIVITY("DWRPMA"),
    DAP_WALLET_REDEEM_POINT_HISTORY_ACTIVITY("DWRPHA"),
    DAP_WALLET_REDEEM_POINT_STADISTICS_ACTIVITY("DWRPSA"),
    DAP_WALLET_REDEEM_POINT_DETAILS_ACTIVITY("DWRPD");

    private String key;

    WalletRedeemPointFragmentsEnumType(String key) {
        this.key = key;
    }

    public static WalletRedeemPointFragmentsEnumType getValue(String name) {
        for (WalletRedeemPointFragmentsEnumType fragments : WalletRedeemPointFragmentsEnumType.values()) {
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
