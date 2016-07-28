package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.07.19..
 * Modified by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 */
public enum WalletCategory implements FermatEnum {

    BRANDED_NICHE_WALLET("BRDNW"),
    BRANDED_REFERENCE_WALLET("BRDRW"),
    REFERENCE_WALLET("REFW"),
    NICHE_WALLET("NCHW");

    private String code;

    WalletCategory(String code) {
        this.code = code;
    }

    public static WalletCategory getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "BRDNW":
                return BRANDED_NICHE_WALLET;
            case "BRDRW":
                return BRANDED_REFERENCE_WALLET;
            case "REFW":
                return REFERENCE_WALLET;
            case "NCHW":
                return NICHE_WALLET;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the WalletCategory enum");
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
