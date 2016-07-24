package com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 09/06/15.
 */
public enum TransactionStatus {
    ACKNOWLEDGED("ACK"),
    RESPONSIBLE("RES"),
    DELIVERED("DLV"),
    APPLIED("APP");

    private final String code;

    TransactionStatus(String Code) {
        this.code = Code;
    }

    public String getCode() {
        return this.code;
    }

    public static TransactionStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "ACK":
                return TransactionStatus.ACKNOWLEDGED;
            case "RES":
                return TransactionStatus.RESPONSIBLE;
            case "DLV":
                return TransactionStatus.DELIVERED;
            case "APP":
                return TransactionStatus.APPLIED;
            //Modified by Manuel Perez on 04/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the TransactionStatus enum");

        }

        /**
         * If we try to cpmvert am invalid string.
         */
        //throw new InvalidParameterException(code);
    }
}
