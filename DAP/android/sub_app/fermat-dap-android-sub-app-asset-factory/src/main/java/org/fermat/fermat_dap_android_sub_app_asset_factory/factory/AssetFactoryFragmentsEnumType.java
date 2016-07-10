package org.fermat.fermat_dap_android_sub_app_asset_factory.factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * AssetFactoryFragmentsEnumType
 *
 * @author Francisco Vasquez on 15/09/15.
 * @version 1.0
 */
public enum AssetFactoryFragmentsEnumType implements FermatFragmentsEnumType<AssetFactoryFragmentsEnumType> {


    DAP_SUB_APP_ASSET_FACTORY_EDITABLE_TAB_FRAGMENT("DAPSAAFETF"),
    DAP_SUB_APP_ASSET_FACTORY_PUBLISHED_TAB_FRAGMENT("DAPSAAFPTF"),
    DAP_SUB_APP_ASSET_EDITOR_ACTIVITY("DSAAEA"),
    DAP_SUB_APP_ASSET_FACTORY_SETTINGS("DSAAFS"),
    DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NETWORK_MAIN("DSAAFSNM"),
    DAP_SUB_APP_ASSET_FACTORY_SETTINGS_NOTIFICATIONS("DSAAFN"),

    DAP_SUB_APP_ASSET_FACTORY_WIZARD_MULTIMEDIA("DSAAFWM"),
    DAP_SUB_APP_ASSET_FACTORY_WIZARD_PROPERTIES("DSAAFWP"),
    DAP_SUB_APP_ASSET_FACTORY_WIZARD_CRYPTO("DSAAFWC"),
    DAP_SUB_APP_ASSET_FACTORY_WIZARD_VERIFY("DSAAFWV");

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
