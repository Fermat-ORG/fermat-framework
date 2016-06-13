package org.fermat.fermat_dap_android_wallet_redeem_point.factory;

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
    DAP_WALLET_REDEEM_POINT_SETTINGS_MAIN_NETWORK("DWRPSMN"),
    DAP_WALLET_REDEEM_POINT_ASSET_SETTINGS_ACTIVITY("DWRPASA"),
    DAP_WALLET_REDEEM_POINT_ASSET_SETTINGS_NOTIFICATIONS("DWRPASN"),
    DAP_WALLET_REDEEM_POINT_DETAILS_USERS_TAB("DWRPDUT"),
    DAP_WALLET_REDEEM_POINT_DETAILS_TRANSACTIONS_TAB("DWRPDTT"),

    // Enums for DAP V3
    DAP_WALLET_REDEEM_POINT_MAIN_PENDING_TAB_FRAGMENT("DWRPMPTF"),
    DAP_WALLET_REDEEM_POINT_MAIN_HISTORY_TAB_FRAGMENT("DWRPMHTF"),
    DAP_WALLET_REDEEM_POINT_USER_DETAIL_FRAGMENT("DWRPUDF");


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
