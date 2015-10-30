package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 */
public enum CurrencyType implements FermatEnum {
    CRYPTO_MONEY("CRYP"),
    BANK_MONEY("BANK"),
    CASH_ON_HAND_MONEY("CASHOH"),
    CASH_DELIVERY_MONEY("CASHD");

    private String code;

    CurrencyType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static CurrencyType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "CRYP": return CurrencyType.CRYPTO_MONEY;
            case "BANK": return CurrencyType.BANK_MONEY;
            case "CASHOH":
                return CurrencyType.CASH_ON_HAND_MONEY;
            case "CASHD":
                return CurrencyType.CASH_DELIVERY_MONEY;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
