package com.bitdubai.fermat_cbp_api.all_definition.enums;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
public enum IdentityPublished {
    PUBLISHED("1"),
    UNPUBLISHED("0");

    private final String code;

    IdentityPublished(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static IdentityPublished getByCode(String code) {

        switch (code) {
            case "1":      return PUBLISHED;
            case "0": return UNPUBLISHED;
            default:          return UNPUBLISHED;
        }
    }
}