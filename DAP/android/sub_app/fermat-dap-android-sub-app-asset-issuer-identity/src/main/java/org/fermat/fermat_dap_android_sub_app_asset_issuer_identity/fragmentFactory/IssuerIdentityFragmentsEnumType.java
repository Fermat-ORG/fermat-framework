package org.fermat.fermat_dap_android_sub_app_asset_issuer_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum IssuerIdentityFragmentsEnumType implements FermatFragmentsEnumType<IssuerIdentityFragmentsEnumType> {

    DAP_SUB_APP_ASSET_ISSUER_IDENTITY_MAIN_FRAGMENT("DSAAIIMF"),
    DAP_SUB_APP_ASSET_ISSUER_IDENTITY_CREATE_IDENTITY_FRAGMENT("DSAAIICIF"),
    DAP_SUB_APP_ASSET_ISSUER_IDENTITY_GEOLOCATION_FRAGMENT("DSAAIIGF"),;

    private String key;

    IssuerIdentityFragmentsEnumType(String key) {
        this.key = key;
    }

    public static IssuerIdentityFragmentsEnumType getValue(String name) {
        for (IssuerIdentityFragmentsEnumType fragments : IssuerIdentityFragmentsEnumType.values()) {
            if (fragments.key.equals(name)) {
                return fragments;
            }
        }
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
