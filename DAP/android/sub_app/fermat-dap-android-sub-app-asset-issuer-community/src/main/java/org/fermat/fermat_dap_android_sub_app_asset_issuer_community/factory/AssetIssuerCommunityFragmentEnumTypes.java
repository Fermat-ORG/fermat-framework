package org.fermat.fermat_dap_android_sub_app_asset_issuer_community.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by francisco on 21/10/15.
 */
public enum AssetIssuerCommunityFragmentEnumTypes implements FermatFragmentsEnumType<AssetIssuerCommunityFragmentEnumTypes> {

    DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_MAIN("DAPAICAM"),
    DAP_ASSET_ISSUER_COMMUNITY_ACTIVITY_PROFILE_FRAGMENT("DAICAPF"),
    DAP_ASSET_ISSUER_COMMUNITY_CONNECTIONS_LIST_FRAGMENT("DAICCLF"),
    DAP_ASSET_ISSUER_COMMUNITY_NOTIFICATIONS_FRAGMENT("DAICNF"),;
//    DAP_ASSET_ISSUER_COMMUNITY_SETTINGS("DAICS");
    //DAP_ASSET_ISSUER_COMMUNITY_SETTINGS_FRAGMENT("DAICS");

    private String key;

    AssetIssuerCommunityFragmentEnumTypes(String key) {
        this.key = key;
    }

    public static AssetIssuerCommunityFragmentEnumTypes getValue(String name) {
        for (AssetIssuerCommunityFragmentEnumTypes fragments : AssetIssuerCommunityFragmentEnumTypes.values()) {
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
