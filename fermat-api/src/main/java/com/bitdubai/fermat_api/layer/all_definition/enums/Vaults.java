package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Enums the vaults on Fermat.
 * Created by Leon Acosta (laion.cj91@gmail.com) on 02/09/2015.
 */
public enum Vaults {

    ASSETS_OVER_BITCOIN_VAULT("AOBV"),
    BITCOIN_VAULT("BITV");

    private String code;

    Vaults(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Vaults getByCode(String code) throws InvalidParameterException {

        for (Vaults vault : Vaults.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the Vaults enum.");
    }
}
