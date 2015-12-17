package com.bitdubai.sub_app.crypto_broker_community.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public enum CryptoBrokerCommunityFragmentsEnumType implements FermatFragmentsEnumType<CryptoBrokerCommunityFragmentsEnumType> {

   /* CONNECTIONS               ("CNN"),
    CONNECTIONS_LIST          ("CLI"),
    CONNECTIONS_NOTIFICATIONS ("CNT"),
    CONNECTIONS_OTHER_PROFILE ("COP"),
    CONNECTIONS_REGISTER_USER ("CRU"),
    CONNECTIONS_REQUEST       ("CRQ"),
    CONNECTIONS_SETTINGS      ("CST"),
    CONNECTIONS_WORLD         ("CWD"),
    MAIN                      ("MAI"),*/

    CWP_WALLET_STORE_MAIN_ACTIVITY("CWSMA"),
    CWP_WALLET_STORE_DETAIL_ACTIVITY("CWSDA"),
    CWP_WALLET_STORE_MORE_DETAIL_ACTIVITY("CWSMDA"),
    CWP_WALLET_STORE_ALL_FRAGMENT("CWSAF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_FRAGMENT("CCPSAIUCF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTIONS_FRAGMENT("CCPSAIUCCF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_DETAIL_FRAGMENT("CCPSAIUCCDF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_NOTIFICATIONS_FRAGMENT("CCPSAIUCCNF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_OTHER_PROFILE_FRAGMENT("CCPSAIUCCOPF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_WORLD_FRAGMENT("CCPSAIUCCWF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_FRIEND_LIST_FRAGMENT("CCPSAIUCCFLF"),
    CCP_SUB_APP_INTRA_USER_COMMUNITY_CONNECTION_SETTINGS_FRAGMENT("CCPSAIUCCSF"),


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

    ;

    private final String key;

    CryptoBrokerCommunityFragmentsEnumType(final String key) {

        this.key = key;
    }

    public static CryptoBrokerCommunityFragmentsEnumType getValue(final String name) {

        for (CryptoBrokerCommunityFragmentsEnumType fragments : CryptoBrokerCommunityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        // todo is it ok?
        return null;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String toString() {
        return key;
    }
}
