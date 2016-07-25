package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
public enum IdentityPublished implements FermatEnum {
    PUBLISHED("1"),
    UNPUBLISHED("0");

    private final String code;

    IdentityPublished(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static IdentityPublished getByCode(String code) {

        switch (code) {
            case "1":
                return PUBLISHED;
            case "0":
                return UNPUBLISHED;
            default:
                return UNPUBLISHED;
        }
    }
}