package com.bitdubai.fermat_cbp_api.all_definition.enums;

import java.security.InvalidParameterException;

public enum NegotiationStepType {

    EXCHANGE_RATE("EXCRAT"),
    AMOUNT_TO_SALE("AMOUTOSAL"),
    PAYMENT_METHOD("PAYMET"),
    CUSTOMER_BANK_ACCOUNT("CUSBANACC"),
    BROKER_BANK_ACCOUNT("BROBANACC"),
    BROKER_LOCATION("BROPLADEL"),
    DATE_TIME_TO_DELIVER("BRODATDEL"),
    CUSTOMER_LOCATION("CUSPLADEL"),
    DATE_TIME_TO_PAY("CUSDATDEL"),
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
                throw new InvalidParameterException(new StringBuilder().append("Code Received: ").append(code).append(" - This Code Is Not Valid for the ClauseType enum").toString());
        }
    }
}

