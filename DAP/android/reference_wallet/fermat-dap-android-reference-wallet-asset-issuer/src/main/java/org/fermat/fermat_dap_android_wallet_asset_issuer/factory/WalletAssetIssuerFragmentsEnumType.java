package org.fermat.fermat_dap_android_wallet_asset_issuer.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * WalletAssetIssuerFragmentsEnumType
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public enum WalletAssetIssuerFragmentsEnumType implements FermatFragmentsEnumType<WalletAssetIssuerFragmentsEnumType> {


    DAP_WALLET_ASSET_ISSUER_MAIN_ACTIVITY("DWAIMA"),
    DAP_WALLET_ASSET_ISSUER_MAIN_SETTINGS_ACTIVITY("DWAIMSA"),
    DAP_WALLET_ASSET_ISSUER_SETTINGS_MAIN_NETWORK_ACTIVITY("DWAISMNA"),
    DAP_WALLET_ASSET_ISSUER_SETTINGS_NOTIFICATIONS_ACTIVITY("DWAISNA"),
    //    DAP_WALLET_ASSET_ISSUER_HISTORY_ACTIVITY("DWAIHA"),
//    DAP_WALLET_ASSET_ISSUER_STADISTICS_ACTIVITY("DWAISA"),
    DAP_WALLET_ASSET_ISSUER_ASSET_DETAIL("DWAIAD"),
    DAP_WALLET_ASSET_ISSUER_USER_DELIVERY_LIST("DWAIUDL"),
    DAP_WALLET_ASSET_ISSUER_USER_DELIVERY_LIST_HEADER("DWAIUDLH"),
    DAP_WALLET_ASSET_ISSUER_USER_REDEEMED_LIST("DWAIURL"),
    //    DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY("DWAIADL"),
    DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY_TAB_SELECT_USERS("DWAIADTSU"),

    DAP_WALLET_ASSET_ISSUER_USER_APPROPIATE_LIST("DWAIUAL"),

    DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY_TAB_SELECT_GROUPS("DWAIADTSG"),
    DAP_WALLET_ASSET_ISSUER_ASSET_DELIVERY_TABS("DWAIADT");


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
