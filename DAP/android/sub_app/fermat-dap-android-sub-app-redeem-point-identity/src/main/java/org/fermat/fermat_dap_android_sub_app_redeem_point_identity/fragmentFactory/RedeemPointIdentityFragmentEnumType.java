package org.fermat.fermat_dap_android_sub_app_redeem_point_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum RedeemPointIdentityFragmentEnumType implements FermatFragmentsEnumType<RedeemPointIdentityFragmentEnumType> {

    DAP_SUB_APP_REDEEM_POINT_IDENTITY_MAIN_FRAGMENT("DAPSARPIMF"),
    DAP_SUB_APP_REDEEM_POINT_IDENTITY_CREATE_IDENTITY_FRAGMENT("DAPSARPICIF"),
    DAP_SUB_APP_REDEEM_POINT_IDENTITY_GEOLOCATION_FRAGMENT("DAPSARPIGF"),

    ;

    private String key;

    RedeemPointIdentityFragmentEnumType(String key) {
        this.key = key;
    }

    public static RedeemPointIdentityFragmentEnumType getValue(String name) {
        for (RedeemPointIdentityFragmentEnumType fragments : RedeemPointIdentityFragmentEnumType.values()) {
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
