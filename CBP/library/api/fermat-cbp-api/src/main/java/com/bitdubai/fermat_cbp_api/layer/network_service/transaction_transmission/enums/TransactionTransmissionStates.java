package com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/11/15.
 */
public enum TransactionTransmissionStates {

    CONFIRM_CONTRACT("CCX"),
    CONFIRM_RESPONSE("CRX"),
    NOT_READY_TO_SEND("NRD"),
    PRE_PROCESSING_SEND("PPS"),
    SEEN_BY_DESTINATION_NETWORK_SERVICE("SBDNS"),
    SEEN_BY_OWN_NETWORK_SERVICE("SBONS"),
    SENT("SENT"),
    SENDING_HASH("HTX"),
    UPDATE_CONTRACT("UCX");

    String code;

    TransactionTransmissionStates(String code) {
        this.code = code;
    }


    public static TransactionTransmissionStates getByCode(String code) throws InvalidParameterException {

        for (TransactionTransmissionStates transactionTransmissionStates : TransactionTransmissionStates.values()) {
            if (transactionTransmissionStates.getCode().equals(code))
                return transactionTransmissionStates;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This code is not valid for the TransactionTransmissionStates enum.");
    }

    public static TransactionTransmissionStates getByTransactionTransmissionStates(TransactionTransmissionStates transactionTransmissionStates) throws InvalidParameterException {

        for (TransactionTransmissionStates states : TransactionTransmissionStates.values()) {
            if (states.getCode().equals(transactionTransmissionStates))
                return transactionTransmissionStates;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("cryptoTransmissionStates: ").append(transactionTransmissionStates).toString(), "This cryptoTransmissionStates is not valid for the transactionTransmissionStates enum.");
    }

    public String getCode() {
        return this.code;
    }

}
