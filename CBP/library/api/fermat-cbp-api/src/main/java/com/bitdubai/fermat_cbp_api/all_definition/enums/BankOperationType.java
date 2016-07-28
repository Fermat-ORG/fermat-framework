package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */

public enum BankOperationType implements FermatEnum {

    DEPOSIT("DEP"),
    TRANSFER("TRA");

    private String code;

    BankOperationType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static BankOperationType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DEP":
                return BankOperationType.DEPOSIT;
            case "TRA":
                return BankOperationType.TRANSFER;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the BankOperationType enum");
        }
    }

}
