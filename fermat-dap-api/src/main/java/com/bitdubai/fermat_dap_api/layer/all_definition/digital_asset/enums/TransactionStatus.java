package com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/09/15.
 */
public enum TransactionStatus {
    FORMING_GENESIS("FGEN");

    private String code;

    TransactionStatus (String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static TransactionStatus getByCode(String code)throws InvalidParameterException {
        switch (code) {
            case "FGEN":
                return TransactionStatus.FORMING_GENESIS;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the State enum.");
        }
    }
}
