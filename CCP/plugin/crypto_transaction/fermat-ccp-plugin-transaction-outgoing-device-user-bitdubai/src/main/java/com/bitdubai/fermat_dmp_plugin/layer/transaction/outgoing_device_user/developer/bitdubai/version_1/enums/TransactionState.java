package com.bitdubai.fermat_dmp_plugin.layer.transaction.outgoing_device_user.developer.bitdubai.version_1.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Joaquin Carrasquero on 17/03/16.
 */
public enum TransactionState {



    SENT_TO_WALLET("STW"),
    COMPLETED("COM"),
    CANCELED("CLD");


    private final String code;

    TransactionState(String code){
        this.code = code;
    }

    public String getCode() {return this.code;}

    public static TransactionState getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "STW": return TransactionState.SENT_TO_WALLET;
            case "COM":   return TransactionState.COMPLETED;
            case "CLD":  return TransactionState.CANCELED;
            default:
                /**
                 * If we try to cpmvert am invalid string.
                 */
                throw new InvalidParameterException(code);
        }
    }

}
