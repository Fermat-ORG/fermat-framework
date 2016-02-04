package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by angel on 18/9/15.
 * Updated by Manuel Perez on 06/01/2015
 */
public enum CurrencyType implements FermatEnum {

    BANK_MONEY          ("BAT", "Bank"         ),
    CASH_DELIVERY_MONEY ("COH", "Cash Delivery"),
    CASH_ON_HAND_MONEY  ("CAD", "Cash on Hand" ),
    CRYPTO_MONEY        ("CRT", "Crypto"       ),

    ;

    private final String code, friendlyName;

    CurrencyType(final String code        ,
                 final String friendlyName) {

        this.code         = code        ;
        this.friendlyName = friendlyName;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public String getFriendlyName() {
        return this.friendlyName;
    }

    public static CurrencyType getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "BAT":return BANK_MONEY         ;
            case "COH":return CASH_DELIVERY_MONEY;
            case "CAD":return CASH_ON_HAND_MONEY ;
            case "CRT":return CRYPTO_MONEY       ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code is not valid for the CurrencyType enum."
                );
        }
    }
}
