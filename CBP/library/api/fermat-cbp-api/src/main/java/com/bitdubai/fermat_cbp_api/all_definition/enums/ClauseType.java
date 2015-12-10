package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by jorge on 12-10-2015.
 */

public enum ClauseType implements FermatEnum {
    CUSTOMER_CURRENCY("CUSCUR"), // Moneda que recibe como mercancia el customer

    BROKER_CURRENCY("BROCUR"),   // Moneda que recibe como pago el broker

    EXCHANGE_RATE("EXCRAT"), // tasa de cambio

    CUSTOMER_CURRENCY_QUANTITY("CUSCURQUA"), // Cantidad de mercancia que recibe el customer
    BROKER_CURRENCY_QUANTITY("CUSBROQUA"),   // Cantidad de pago que recibe el broker


    CUSTOMER_PAYMENT_METHOD("CUSPAYMET"),  // Forma en la que paga el customer
    BROKER_PAYMENT_METHOD("BROPAYMET"),    // Forma en la que entrega la mercancia el broker

    CUSTOMER_CRYPTO_ADDRESS("CUSCRYADD"),  // Direccion bitcoin del customer
    BROKER_CRYPTO_ADDRESS("BROCRYADD"),    // Direccion bitcoin del Broker
/*
    CUSTOMER_BANK("CUSBAN"), // No es necesaria
    BROKER_BANK("BROBAN"), // No es necesaria
*/

    CUSTOMER_BANK_ACCOUNT("CUSBANACC"),  // Estas clausulas ya contienen toda la informacion
    BROKER_BANK_ACCOUNT("BROBANACC"),   //  necesaria sobre las cuenta bancaria

/*
    PLACE_TO_MEET("PLAMEE"),        // No es necesaria
    DATE_TIME_TO_MEET("DATTIMMEE"), // No es necesaria
*/
    BROKER_PLACE_TO_DELIVER("BROPLADEL"),     // Lugar y hora donde el
    BROKER_DATE_TIME_TO_DELIVER("BRODATDEL"), // Broker recibira el pago

    CUSTOMER_PLACE_TO_DELIVER("CUSPLADEL"),     // Lugar y hora donde el
    CUSTOMER_DATE_TIME_TO_DELIVER("CUSDATDEL"); //  Customer recibira la mercancia

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
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the ClauseType enum");
        }
    }
}
