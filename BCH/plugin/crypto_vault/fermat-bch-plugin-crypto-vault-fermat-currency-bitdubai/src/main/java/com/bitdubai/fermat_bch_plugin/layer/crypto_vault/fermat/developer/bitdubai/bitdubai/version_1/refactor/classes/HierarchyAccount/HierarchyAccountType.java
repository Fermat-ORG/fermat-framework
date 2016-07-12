package com.bitdubai.fermat_bch_plugin.layer.crypto_vault.fermat.developer.bitdubai.bitdubai.version_1.refactor.classes.HierarchyAccount;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 12/16/15.
 */
public enum HierarchyAccountType {
    MASTER_ACCOUNT("MASTER"),
    REDEEMPOINT_ACCOUNT("RPOINT");

    private String code;

    HierarchyAccountType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static HierarchyAccountType getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "MASTER":
                return MASTER_ACCOUNT;
            case "RPOINT":
                return REDEEMPOINT_ACCOUNT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the CryptoVaults enum.");
        }
    }
}
