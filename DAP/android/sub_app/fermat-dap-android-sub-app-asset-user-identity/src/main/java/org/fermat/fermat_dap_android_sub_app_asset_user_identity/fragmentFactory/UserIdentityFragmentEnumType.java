package org.fermat.fermat_dap_android_sub_app_asset_user_identity.fragmentFactory;

import com.bitdubai.fermat_android_api.layer.definition.wallet.enums.FermatFragmentsEnumType;

/**
 * Created by Matias Furszyfer on 2015.07.22..
 */

public enum UserIdentityFragmentEnumType implements FermatFragmentsEnumType<UserIdentityFragmentEnumType> {

    DAP_SUB_APP_ASSET_USER_IDENTITY_MAIN_FRAGMENT("DAPSAAUIMF"),
    DAP_SUB_APP_ASSET_USER_IDENTITY_CREATE_IDENTITY_FRAGMENT("DAPSAAUICIF"),
    DAP_SUB_APP_ASSET_USER_IDENTITY_GEOLOCATION_FRAGMENT("DSAAUIGF"),;

    private String key;

    UserIdentityFragmentEnumType(String key) {
        this.key = key;
    }

    public static UserIdentityFragmentEnumType getValue(String name) {
        for (UserIdentityFragmentEnumType fragments : UserIdentityFragmentEnumType.values()) {
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
