package com.bitdubai.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 08/09/15.
 */
public enum TransactionStatus {
    FORMING_GENESIS("FGEN"),
    GENESIS_OBTAINED("OGEN"),
    ISSUED("ISSUED");

    private String code;

    TransactionStatus (String code) {
        this.code = code;
    }

    public String getCode() { return this.code ; }

    public static TransactionStatus getByCode(String code)throws InvalidParameterException {
        switch (code) {
            case "FGEN":
                return TransactionStatus.FORMING_GENESIS;
            case "OGEN":
                return TransactionStatus.GENESIS_OBTAINED;
            case "ISSUED":
                return TransactionStatus.ISSUED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the State enum.");
        }
    }
}
