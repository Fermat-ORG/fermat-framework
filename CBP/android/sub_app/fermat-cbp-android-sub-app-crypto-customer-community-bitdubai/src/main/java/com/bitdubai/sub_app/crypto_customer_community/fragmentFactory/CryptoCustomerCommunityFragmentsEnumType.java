package com.bitdubai.sub_app.crypto_customer_community.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoCustomerCommunityFragmentsEnumType implements FermatFragmentsEnumType<CryptoCustomerCommunityFragmentsEnumType> {

    CWP_WALLET_STORE_ALL_FRAGMENT("CWSAF"),

    CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTIONS("CBPSACCCCF"),
    CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_DETAIL("CBPSACCCCDF"),
    CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_NOTIFICATIONS("CBPSACCCCNF"),
    CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_OTHER_PROFILE("CBPSACCCCOPF"),
    CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_WORLD("CBPSACCCCWF"),
    CBP_SUB_APP_CRYPTO_CUSTOMER_COMMUNITY_CONNECTION_FRIEND_LIST("CBPSACCCCFLF"),;

    private String key;

    CryptoCustomerCommunityFragmentsEnumType(String key) {
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

    public static CryptoCustomerCommunityFragmentsEnumType getValue(String name) {
        for (CryptoCustomerCommunityFragmentsEnumType fragments : CryptoCustomerCommunityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
