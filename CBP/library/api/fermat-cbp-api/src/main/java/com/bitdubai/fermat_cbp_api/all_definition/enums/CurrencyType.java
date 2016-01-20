package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 * Updated by Manuel Perez on 06/01/2015
 */
public enum CurrencyType implements FermatEnum {
    CRYPTO_MONEY("CRT"),
    BANK_MONEY("BAT"),
    CASH_ON_HAND_MONEY("CAD"),
    CASH_DELIVERY_MONEY("COH");

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
            case "CRT": return CurrencyType.CRYPTO_MONEY;
            case "BAT": return CurrencyType.BANK_MONEY;
            case "CAD": return CurrencyType.CASH_ON_HAND_MONEY;
            case "COH": return CurrencyType.CASH_DELIVERY_MONEY;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ContactState enum");
        }
    }
}
