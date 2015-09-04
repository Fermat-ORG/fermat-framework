package com.bitdubai.fermat_dap_api.all_definition.digital_asset.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by rodrigo on 9/4/15.
 */
public enum ContractPropertyName {
    REDEEMABLE ("RDI"),
    TRANSFERABLE ("TFR"),
    EXPIRATION_DATE ("EXD");

    private String code;

    ContractPropertyName(String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static ContractPropertyName getByCode(String code)throws InvalidParameterException {
        switch (code) {
            case "RDI":
                return ContractPropertyName.REDEEMABLE;
            case "TFR":
                return ContractPropertyName.TRANSFERABLE;
            case "EXD":
                return ContractPropertyName.EXPIRATION_DATE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Contract Property name.");
        }
    }
}
