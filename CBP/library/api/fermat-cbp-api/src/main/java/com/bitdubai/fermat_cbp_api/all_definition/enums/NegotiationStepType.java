package com.bitdubai.fermat_cbp_api.all_definition.enums;

import java.security.InvalidParameterException;

public enum NegotiationStepType {

    /**
     * Tasa de cambio
     */
    EXCHANGE_RATE("EXCRAT"),
    /**
     * Cantidad a Vender
     */
    AMOUNT_TO_SALE("AMOUTOSAL"),
    /**
     * Metodo de pago que acepta el broker (Crypto, Cash in Hand, Cash Delivery, Bank)
     */
    PAYMENT_METHOD("PAYMET"),
    /**
     * Contiene toda la informacion necesaria sobre las cuenta bancaria del Customer
     */
    CUSTOMER_BANK_ACCOUNT("CUSBANACC"),
    /**
     * Contiene toda la informacion necesaria sobre las cuenta bancaria del Broker
     */
    BROKER_BANK_ACCOUNT("BROBANACC"),
    /**
     * Lugar donde el Broker recibira el pago
     */
    BROKER_LOCATION("BROPLADEL"),
    /**
     * Fecha y hora limite en la que el Broker debe recibir el pago
     */
    DATE_TIME_TO_DELIVER("BRODATDEL"),
    /**
     * Lugar donde el Customer recibira el pago
     */
    CUSTOMER_LOCATION("CUSPLADEL"),
    /**
     * Fecha y hora limite en la que el Customer debe recibir el pago
     */
    DATE_TIME_TO_PAY("CUSDATDEL"),
    /**
     * Fecha y hora limite en la que se mantiene la negociacion
     */
    EXPIRATION_DATE_TIME("EXPDATI");

    private final String code;

    NegotiationStepType(final String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static NegotiationStepType getByCode(final String code) throws InvalidParameterException {
        switch (code) {
            case "EXCRAT":
                return EXCHANGE_RATE;
            case "CUSCURQUA":
                return AMOUNT_TO_SALE;
            case "PAYMET":
                return PAYMENT_METHOD;
            case "CUSBANACC":
                return CUSTOMER_BANK_ACCOUNT;
            case "BROBANACC":
                return BROKER_BANK_ACCOUNT;
            case "BROPLADEL":
                return BROKER_LOCATION;
            case "BRODATDEL":
                return DATE_TIME_TO_DELIVER;
            case "CUSPLADEL":
                return CUSTOMER_LOCATION;
            case "CUSDATDEL":
                return DATE_TIME_TO_PAY;
            case "EXPDATI":
                return EXPIRATION_DATE_TIME;
            default:
                throw new InvalidParameterException("Code Received: " + code +
                        " - This Code Is Not Valid for the ClauseType enum");
        }
    }
}

