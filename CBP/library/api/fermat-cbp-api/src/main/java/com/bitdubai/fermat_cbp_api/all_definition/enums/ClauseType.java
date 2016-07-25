package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;


/**
 * Created by jorge on 12-10-2015.
 */

public enum ClauseType implements FermatEnum {
    /**
     * Moneda que recibe el customer como mercancia
     */
    CUSTOMER_CURRENCY("CUSCUR"),

    /**
     * Moneda que recibe el broker como pago
     */
    BROKER_CURRENCY("BROCUR"),

    /**
     * Tasa de cambio
     */
    EXCHANGE_RATE("EXCRAT"),

    /**
     * Cantidad de mercancia que recibe el customer
     */
    CUSTOMER_CURRENCY_QUANTITY("CUSCURQUA"),
    /**
     * Cantidad de pago que recibe el broker
     */
    BROKER_CURRENCY_QUANTITY("CUSBROQUA"),

    /**
     * Forma en la que paga el customer
     */
    CUSTOMER_PAYMENT_METHOD("CUSPAYMET"),
    /**
     * Forma en la que entrega la mercancia el broker
     */
    BROKER_PAYMENT_METHOD("BROPAYMET"),

    /**
     * Direccion bitcoin del customer
     */
    CUSTOMER_CRYPTO_ADDRESS("CUSCRYADD"),
    /**
     * Direccion bitcoin del Broker
     */
    BROKER_CRYPTO_ADDRESS("BROCRYADD"),

    /**
     * La cuenta bancaria del customer
     */
    CUSTOMER_BANK_ACCOUNT("CUSBANACC"),
    /**
     * La cuenta bancaria del broker
     */
    BROKER_BANK_ACCOUNT("BROBANACC"),

    /**
     * Lugar donde el broker recibira el pago
     */
    BROKER_PLACE_TO_DELIVER("BROPLADEL"),
    /**
     * Fecha y hora en la que el broker recibira el pago
     */
    BROKER_DATE_TIME_TO_DELIVER("BRODATDEL"),

    /**
     * Lugar donde el customer recibira la mercancia
     */
    CUSTOMER_PLACE_TO_DELIVER("CUSPLADEL"),
    /**
     * Fecha y hora en la que el customer recibira la mercancia
     */
    CUSTOMER_DATE_TIME_TO_DELIVER("CUSDATDEL"),

    /*TIME ZONE*/
    BROKER_TIME_ZONE("BROTIMZON"),
    CUSTOMER_TIME_ZONE("CUSTIMZON");

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
            /*
            case "CUSBAN":
                return CUSTOMER_BANK;
            case "BROBAN":
                return BROKER_BANK;
                */
            case "CUSBANACC":
                return CUSTOMER_BANK_ACCOUNT;
            case "BROBANACC":
                return BROKER_BANK_ACCOUNT;
            /*
            case "PLAMEE":
                return PLACE_TO_MEET;
            case "DATTIMMEE":
                return DATE_TIME_TO_MEET;
            */
            case "BROPLADEL":
                return BROKER_PLACE_TO_DELIVER;
            case "BRODATDEL":
                return BROKER_DATE_TIME_TO_DELIVER;
            case "CUSPLADEL":
                return CUSTOMER_PLACE_TO_DELIVER;
            case "CUSDATDEL":
                return CUSTOMER_DATE_TIME_TO_DELIVER;
            case "BROTIMZON":
                return BROKER_TIME_ZONE;
            case "CUSTIMZON":
                return CUSTOMER_TIME_ZONE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the ClauseType enum");
        }
    }
}
