package com.bitdubai.fermat_ccp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * TODO description
 *
 * Created by franklin on 21/11/15.
 */

public enum CryptoTransactionStatus implements FermatEnum {

    /**
     * Please keep the elements of the enum ordered alphabetically.
     */

    ACKNOWLEDGED  ("ACK"),
    CONFIRMED     ("CON"),
    PENDING       ("PEN"),
    REJECTED      ("REJ"),

    ;

    private final String code;

    CryptoTransactionStatus(final String code) {
        this.code = code;
    }

    public static CryptoTransactionStatus getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "ACK":return ACKNOWLEDGED;
            case "CON":return CONFIRMED   ;
            case "PEN":return PENDING     ;
            case "REJ":return REJECTED    ;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This is an invalid CryptoTransactionStatus code"
                );
        }

    }

    @Override
    public final String getCode() {
        return this.code;
    }

}