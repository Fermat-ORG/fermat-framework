package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by jorge on 12-10-2015.
 */

public enum ClauseType implements FermatEnum {
    CUSTOMER_CURRENCY("CUSCUR"),
    BROKER_CURRENCY("BROCUR"),

    EXCHANGE_RATE("EXCRAT"),

    CUSTOMER_CURRENCY_QUANTITY("CUSCURQUA"),
    BROKER_CURRENCY_QUANTITY("CUSBROQUA"),

    CUSTOMER_PAYMENT_METHOD("CUSPAYMET"),
    BROKER_PAYMENT_METHOD("BROPAYMET"),

    /* esto tengo entendido no lo asigna el usuario directamente sino
     la plataforma atraves de del plugin que genera direcciones bitcoin*/
    CUSTOMER_CRYPTO_ADDRESS("CUSCRYADD"),
    BROKER_CRYPTO_ADDRESS("BROCRYADD"),

    CUSTOMER_BANK("CUSBAN"),
    BROKER_BANK("BROBAN"),

    CUSTOMER_BANK_ACCOUNT("CUSBANACC"),
    BROKER_BANK_ACCOUNT("BROBANACC"),

    PLACE_TO_MEET("PLAMEE"),
    DATE_TIME_TO_MEET("DATTIMMEE"),

    BROKER_PLACE_TO_DELIVER("BROPLADEL"),
    BROKER_DATE_TIME_TO_DELIVER("BRODATDEL"),

    CUSTOMER_PLACE_TO_DELIVER("CUSPLADEL"),
    CUSTOMER_DATE_TIME_TO_DELIVER("CUSDATDEL");

    private final String code;

    ClauseType(final String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return code;
    }

    public static ClauseType getByCode(final String code) throws InvalidParameterException {
        switch (code) {
            case "CUSCUR":
                return CUSTOMER_CURRENCY;
            case "EXCRAT":
                return EXCHANGE_RATE;
            case "BROCUR":
                return BROKER_CURRENCY;
            case "CUSCURQUA":
                return CUSTOMER_CURRENCY_QUANTITY;
            case "CUSBROQUA":
                return BROKER_CURRENCY_QUANTITY;
            case "CUSPAYMET":
                return CUSTOMER_PAYMENT_METHOD;
            case "BROPAYMET":
                return BROKER_PAYMENT_METHOD;
            case "CUSCRYADD":
                return CUSTOMER_CRYPTO_ADDRESS;
            case "BROCRYADD":
                return BROKER_CRYPTO_ADDRESS;
            case "CUSBAN":
                return CUSTOMER_BANK;
            case "BROBAN":
                return BROKER_BANK;
            case "CUSBANACC":
                return CUSTOMER_BANK_ACCOUNT;
            case "BROBANACC":
                return BROKER_BANK_ACCOUNT;
            case "PLAMEE":
                return PLACE_TO_MEET;
            case "DATTIMMEE":
                return DATE_TIME_TO_MEET;
            case "BROPLADEL":
                return BROKER_PLACE_TO_DELIVER;
            case "BRODATDEL":
                return BROKER_DATE_TIME_TO_DELIVER;
            case "CUSPLADEL":
                return CUSTOMER_PLACE_TO_DELIVER;
            case "CUSDATDEL":
                return CUSTOMER_DATE_TIME_TO_DELIVER;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ClauseType enum");
        }
    }
}
