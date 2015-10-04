package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 7/25/15.
 */
public enum CatalogItems {
    WALLET ("WLT"),
    LANGUAGE ("LNG"),
    SKIN ("SKN");

    private String code;

    CatalogItems(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static CatalogItems getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "WLT": return CatalogItems.WALLET;
            case "LNG": return CatalogItems.LANGUAGE;
            case "SKN": return CatalogItems.SKIN;
            //Modified by Manuel Perez on 05/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CatalogItems enum");

        }
    }
}
