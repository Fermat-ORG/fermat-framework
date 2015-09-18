package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>TransactionState</code> is used to list the states of a transaction when it is in
 * the process of going out from the system.
 */
public enum TransactionState {
    NEW("NEW"),
    PERSISTED_IN_AVAILABLE ("PIA"),
    PERSISTED_IN_WALLET ("PIW"),
    SENT_TO_CRYPTO_VOULT("STCV"),
    SUCCESSFUL_SENT("SS"),
    RECEIVED ("RCV");


    private final String code;

    TransactionState(String code){
        this.code = code;
    }

    public String getCode() {return this.code;}

    public static TransactionState getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "NEW": return TransactionState.NEW;
            case "PIA": return TransactionState.PERSISTED_IN_AVAILABLE;
            case "PIW": return TransactionState.PERSISTED_IN_WALLET;
            case "STCV": return TransactionState.SENT_TO_CRYPTO_VOULT;
            case "SS": return TransactionState.SUCCESSFUL_SENT;
            case "RCV": return TransactionState.RECEIVED;
                /**
                 * If we try to convert am invalid string.
                 */
                //Modified by Manuel Perez on 05/08/2015
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TransactionState enum");

        }
    }
}
