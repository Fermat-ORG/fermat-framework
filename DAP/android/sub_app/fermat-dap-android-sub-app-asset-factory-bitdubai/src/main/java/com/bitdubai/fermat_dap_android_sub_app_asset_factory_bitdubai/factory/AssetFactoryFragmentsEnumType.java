package com.bitdubai.fermat_dap_android_sub_app_asset_factory_bitdubai.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * AssetFactoryFragmentsEnumType
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public enum AssetFactoryFragmentsEnumType implements FermatFragmentsEnumType<AssetFactoryFragmentsEnumType> {


    DAP_SUB_APP_ASSET_FACTORY_MAIN_ACTIVITY("DSAAFMA"),
    DAP_SUB_APP_ASSET_EDITOR_ACTIVITY("DSAAEA");

    private String key;

    AssetFactoryFragmentsEnumType(String key) {
        this.key = key;
    }

    public static AssetFactoryFragmentsEnumType getValue(String name) {
        for (AssetFactoryFragmentsEnumType fragments : AssetFactoryFragmentsEnumType.values()) {
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
