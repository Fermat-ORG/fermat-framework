package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 * Updated by Manuel Perez on 06/01/2015
 */
public enum MoneyType implements FermatEnum {

    BANK("BAT", "Bank"),
    CASH_DELIVERY("COH", "Cash Delivery"),
    CASH_ON_HAND("CAD", "Cash on Hand"),
    CRYPTO("CRT", "Crypto"),;

    private final String code, friendlyName;

    MoneyType(final String code,
              final String friendlyName) {

        this.code = code;
        this.friendlyName = friendlyName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public static MoneyType getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "BAT":
                return BANK;
            case "COH":
                return CASH_DELIVERY;
            case "CAD":
                return CASH_ON_HAND;
            case "CRT":
                return CRYPTO;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This code is not valid for the MoneyType enum."
                );
        }
    }
}
