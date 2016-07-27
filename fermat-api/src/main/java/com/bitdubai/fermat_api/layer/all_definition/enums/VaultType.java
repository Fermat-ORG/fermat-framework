package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Enums the types of vault in Fermat.
 * Created by Leon Acosta (laion.cj91@gmail.com) on 15/09/2015.
 */
public enum VaultType implements FermatEnum {

    CRYPTO_ASSET_VAULT("ASVA"),
    CRYPTO_CURRENCY_VAULT("CCV"),
    WATCH_ONLY_VAULT("WOV");

    private String code;

    VaultType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static VaultType getByCode(String code) throws InvalidParameterException {

        for (VaultType vault : VaultType.values()) {
            if (vault.getCode().equals(code))
                return vault;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This code is not valid for the VaultType enum.");
    }
}
