package com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import java.io.Serializable;

/**
 * The enum <code>TransactionState</code> is used to list the states of a transaction when it is in
 * the process of going out from the system.
 */
public enum TransactionState implements Serializable {

    NEW("NEW"),
    PERSISTED_IN_AVAILABLE ("PIA"),
    PERSISTED_IN_WALLET ("PIW"),
    SENT_TO_CRYPTO_VAULT("STCV"),
    SUCCESSFUL_SENT("SS"),
    REVERSED ("REV"),
    COMPLETE ("COM"),
    RECEIVED ("RCV");

    private final String code;

    TransactionState(String code){
        this.code = code;
    }

    public String getCode() {return this.code;}

    public static TransactionState getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "NEW":  return NEW;
            case "PIA":  return PERSISTED_IN_AVAILABLE;
            case "PIW":  return PERSISTED_IN_WALLET;
            case "STCV": return SENT_TO_CRYPTO_VAULT;
            case "SS":   return SUCCESSFUL_SENT;
            case "RCV":  return RECEIVED;
            case "REV":  return REVERSED;
            case "COM":  return COMPLETE;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TransactionState enum");
        }
    }
}
