package com.bitdubai.sub_app.wallet_manager.fragment_factory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum DesktopFragmentsEnumType implements FermatFragmentsEnumType<DesktopFragmentsEnumType> {

    DESKTOP_MAIN("DAM"),
    DESKTOP_P2P_MAIN("DP2PM"),
    DESKTOP_TOOLS("DT"),
    SETTINGS("DS"),
    DESKTOP_SOCIAL_MAIN("DSM"),
    COMMUNITIES_FRAGMENT("CF"),

    //settings
    SETTINGS_IMPORT_KEY("DSIK"),
    SETTINGS_EXPORT_KEY("DSEK"),
    MORE_SETTINGS("DMS"),


    // Welcome wizard
    WELCOME_WIZARD_FIRST_SCREEN_FRAGMENT("WWFISF"),
    WELCOME_WIZARD_SECOND_SCREEN_FRAGMENT("WWSSF"),
    WELCOME_WIZARD_THIRD_SCREEN_FRAGMENT("WWTSF"),
    WELCOME_WIZARD_FOURTH_SCREEN_FRAGMENT("WWFOSF"),

    ;

    private String key;

    DesktopFragmentsEnumType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }


    @Override
    public String toString() {
        return key;
    }

    public static DesktopFragmentsEnumType getValue(String name) {
        for (DesktopFragmentsEnumType fragments : DesktopFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
