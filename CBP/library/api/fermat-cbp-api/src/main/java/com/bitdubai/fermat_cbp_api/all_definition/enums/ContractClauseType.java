package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Angel 28/11/2015
 * Updated by Manuel Perez on 22/12/2015
 */
public enum ContractClauseType implements FermatEnum {
    CRYPTO_TRANSFER("CRT"),
    BANK_TRANSFER("BAT"),
    CASH_DELIVERY("CAD"),
    CASH_ON_HAND("COH");

    private final String code;

    ContractClauseType(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static ContractClauseType getByCode(final String code) throws InvalidParameterException {
        switch (code) {
            case "CRT":
                return CRYPTO_TRANSFER;
            case "BAT":
                return BANK_TRANSFER;
            case "CAD":
                return CASH_DELIVERY;
            case "COH":
                return CASH_ON_HAND;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ClauseType enum");
        }
    }
}
