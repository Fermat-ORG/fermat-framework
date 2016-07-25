package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 20/11/15.
 */
public enum BusinessTransactionTransactionType {

    /**
     * Definition types
     */
    ACK_CONFIRM_MESSAGE("ACMS"),
    CONFIRM_MESSAGE("CMS"),
    TRANSACTION_HASH("TXH"),
    CONTRACT_STATUS_UPDATE("CSU");

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    BusinessTransactionTransactionType(String code) {
        this.code = code;
    }

    /**
     * Return a String code
     *
     * @return String that represent of the message status
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return MessagesStatus enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static BusinessTransactionTransactionType getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "CMS":
                return BusinessTransactionTransactionType.CONFIRM_MESSAGE;
            case "TXH":
                return BusinessTransactionTransactionType.TRANSACTION_HASH;
            case "CSU":
                return BusinessTransactionTransactionType.CONTRACT_STATUS_UPDATE;
            case "ACMS":
                return BusinessTransactionTransactionType.ACK_CONFIRM_MESSAGE;
        }

        /**
         * If we try to convert am invalid string.
         */
        throw new InvalidParameterException(code);
    }
}
