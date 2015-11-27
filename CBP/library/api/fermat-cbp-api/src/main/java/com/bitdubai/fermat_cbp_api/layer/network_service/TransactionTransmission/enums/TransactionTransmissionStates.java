package com.bitdubai.fermat_cbp_api.layer.network_service.TransactionTransmission.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 24/11/15.
 */
public enum TransactionTransmissionStates {

    CONFIRM_CONTRACT("CCX"),
    CONFIRM_RESPONSE("CRX"),
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
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This code is not valid for the TransactionTransmissionStates enum.");
    }

    public static TransactionTransmissionStates getByTransactionTransmissionStates(TransactionTransmissionStates transactionTransmissionStates) throws InvalidParameterException {

        for (TransactionTransmissionStates states : TransactionTransmissionStates.values()) {
            if (states.getCode().equals(transactionTransmissionStates))
                return transactionTransmissionStates;
        }
        throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "cryptoTransmissionStates: " + transactionTransmissionStates, "This cryptoTransmissionStates is not valid for the transactionTransmissionStates enum.");
    }

    public String getCode(){
        return this.code;
    }

}
