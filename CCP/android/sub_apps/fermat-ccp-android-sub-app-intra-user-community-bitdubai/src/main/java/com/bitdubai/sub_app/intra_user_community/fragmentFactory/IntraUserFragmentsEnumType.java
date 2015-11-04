package com.bitdubai.sub_app.intra_user_community.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum IntraUserFragmentsEnumType implements FermatFragmentsEnumType<IntraUserFragmentsEnumType> {


    CWP_WALLET_STORE_MAIN_ACTIVITY("CWSMA"),
    CWP_WALLET_STORE_DETAIL_ACTIVITY("CWSDA"),
    CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY("CWSMDA"),
    CWP_WALLET_STORE_ALL_FRAGMENT("CWSAF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_FRAGMENT("CCPSAIUCF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS_FRAGMENT("CCPSAIUCCF"),

    /**
     * Va a ser eliminado. No se usa.
     */
    @Deprecated
    CWP_WALLET_STORE_ACCEPTED_NEARBY_FRAGMENT("CWSANF"),


    @Deprecated
    CWP_WALLET_STORE_FREE_FRAGMENT("CWSFF"),

    @Deprecated
    CWP_WALLET_STORE_PAID_FRAGMENT("CWSPF"),
    /**
     * Va a ser eliminado. No se usa.
     */
    @Deprecated
    CWP_WALLET_STORE_SEARCH_MODE("CWSSM");



    private String key;

    IntraUserFragmentsEnumType(String key) {
        this.key = key;
    }

    IntraUserFragmentsEnumType() {
    }

    @Override
    public String getKey() {
        return this.key;
    }


    @Override
    public String toString() {
        return key;
    }

    public static IntraUserFragmentsEnumType getValue(String name) {
        for (IntraUserFragmentsEnumType fragments : IntraUserFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        // throw an IllegalArgumentException or return null
        // throw new IllegalArgumentException("the given number doesn't match any Status.");
        return null;
    }
}
