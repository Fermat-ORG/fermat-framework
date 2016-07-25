package com.bitdubai.sub_app.crypto_broker_community.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/12/2015.
 *
 * @author lnacosta
 * @version 1.0.0
 */
public enum CryptoBrokerCommunityFragmentsEnumType implements FermatFragmentsEnumType<CryptoBrokerCommunityFragmentsEnumType> {

    CWP_WALLET_STORE_ALL_FRAGMENT("CWSAF"),

    CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTIONS("CBPSACBCCF"),
    CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_DETAIL("CBPSACBCCDF"),
    CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_NOTIFICATIONS("CBPSACBCCNF"),
    CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_OTHER_PROFILE("CBPSACBCCOPF"),
    CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_WORLD("CBPSACBCCWF"),
    CBP_SUB_APP_CRYPTO_BROKER_COMMUNITY_CONNECTION_FRIEND_LIST("CBPSACBCCFLF"),;

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
