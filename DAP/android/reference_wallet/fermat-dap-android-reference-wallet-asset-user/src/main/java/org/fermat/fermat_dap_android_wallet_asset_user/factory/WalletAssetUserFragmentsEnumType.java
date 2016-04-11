package org.fermat.fermat_dap_android_wallet_asset_user.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * WalletAssetIssuerFragmentsEnumType
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public enum WalletAssetUserFragmentsEnumType implements FermatFragmentsEnumType<WalletAssetUserFragmentsEnumType> {


    DAP_WALLET_ASSET_USER_MAIN_ACTIVITY("DWUIMA"),
    DAP_WALLET_ASSET_USER_HISTORY_ACTIVITY("DWAUHA"),
    DAP_WALLET_ASSET_USER_ASSET_DETAIL_TRANSACTIONS("DWAUADT"),
    DAP_WALLET_ASSET_USER_ASSET_DETAIL_RECIEVED_TAB("DWAUADRT"),
    DAP_WALLET_ASSET_USER_ASSET_REDEEM("DWAUAR"),
    DAP_WALLET_ASSET_USER_ASSET_REDEEM_SELECT_REDEEMPOINTS("DWAUARSRP"),
    DAP_WALLET_ASSET_USER_SETTINGS_ACTIVITY("DWAUSA"),
    DAP_WALLET_ASSET_USER_SETTINGS_MAIN_NETWORK("DWAUSMN"),
    DAP_WALLET_ASSET_USER_SETTINGS_NOTIFICATIONS("DWAUSN"),
    DAP_WALLET_ASSET_USER_ASSET_SELL_FRAGMENT("DWAUASF"),
    DAP_WALLET_ASSET_USER_ASSET_SELL_SELECT_USERS_FRAGMENT("DWAUASSUF"),
    DAP_WALLET_ASSET_USER_ASSET_TRANSFER_FRAGMENT("DWAUATF"),
    DAP_WALLET_ASSET_USER_ASSET_TRANSFER_SELECT_USERS_FRAGMENT("DWAUATSUF"),
    DAP_WALLET_ASSET_USER_ASSET_NEGOTIATION_DETAIL_FRAGMENT("DWAUANDF"),
    DAP_WALLET_ASSET_USER_V2_HOME("DWAUH"),
    DAP_WALLET_ASSET_USER_V2_DETAIL("DWAUD"),
    DAP_WALLET_ASSET_USER_V2_REDEEM_POINTS("DWAURP"),
    DAP_WALLET_ASSET_USER_V3_HOME("DWAU3H");

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
