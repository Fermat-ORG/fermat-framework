package com.bitdubai.sub_app.crypto_customer_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum CryptoCustomerIdentityFragmentsEnumType implements FermatFragmentsEnumType<CryptoCustomerIdentityFragmentsEnumType> {

    CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_MAIN_FRAGMENT("CBPSACCIMF"),
    CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_IMAGE_CROPPER_FRAGMENT("CBPSACCIICF"),
    CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_CREATE_IDENTITY_FRAGMENT("CBPSACCICIF"),
    CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_EDIT_IDENTITY_FRAGMENT("CBPSACCIEIF"),
    CBP_SUB_APP_CRYPTO_CUSTOMER_IDENTITY_GEOLOCATION_IDENTITY_FRAGMENT("CBPSACCIGIF");

    private String key;

    CryptoCustomerIdentityFragmentsEnumType(String key) {
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

    public static CryptoCustomerIdentityFragmentsEnumType getValue(String name) {
        for (CryptoCustomerIdentityFragmentsEnumType fragments : CryptoCustomerIdentityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
        return null;
    }
}
