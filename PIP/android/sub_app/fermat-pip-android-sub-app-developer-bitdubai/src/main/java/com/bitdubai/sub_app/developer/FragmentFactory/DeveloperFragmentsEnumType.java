package com.bitdubai.sub_app.developer.FragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum DeveloperFragmentsEnumType implements FermatFragmentsEnumType<DeveloperFragmentsEnumType> {


    CWP_WALLET_DEVELOPER_TOOL_DATABASE_LIST_FRAGMENT("CWDTDLF"),
    CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_LIST_FRAGMENT("CWDTDTLF"),
    CWP_WALLET_DEVELOPER_TOOL_DATABASE_TABLE_RECORD_LIST_FRAGMENT("CWDTDTRLF"),
    CWP_WALLET_DEVELOPER_TOOL_DATABASE_FRAGMENT("CWDTDF"),
    CWP_WALLET_DEVELOPER_TOOL_LOG_FRAGMENT("CWDTLF"),
    CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_1_FRAGMENT("CWDTLL1F"),
    CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_2_FRAGMENT("CWDTLL2F"),
    CWP_WALLET_DEVELOPER_TOOL_LOG_LEVEL_3_FRAGMENT("CWDTLL3F"),;


    private String key;

    DeveloperFragmentsEnumType(String key) {
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

    public static DeveloperFragmentsEnumType getValue(String name) {
        for (DeveloperFragmentsEnumType fragments : DeveloperFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        // throw an IllegalArgumentException or return null
        // throw new IllegalArgumentException("the given number doesn't match any Status.");
        return null;
    }
}
