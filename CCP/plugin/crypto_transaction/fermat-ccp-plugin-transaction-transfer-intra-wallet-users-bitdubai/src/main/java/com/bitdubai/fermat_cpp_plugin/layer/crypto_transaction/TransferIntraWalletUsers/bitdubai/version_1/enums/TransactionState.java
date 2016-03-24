package com.bitdubai.fermat_cpp_plugin.layer.crypto_transaction.TransferIntraWalletUsers.bitdubai.version_1.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Joaquin Carrasquero on 17/03/16.
 */
public enum TransactionState {


    NEW("NEW"),
    SENT_TO_WALLET("STW"),
    COMPLETED("COM"),
    CANCELED("CLD"),
    ERROR("ERR");


    private final String code;

    TransactionState(String code){
        this.code = code;
    }

    public String getCode() {return this.code;}

    public static TransactionState getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "NEW": return TransactionState.NEW;
            case "STW": return TransactionState.SENT_TO_WALLET;
            case "COM":   return TransactionState.COMPLETED;
            case "CLD":  return TransactionState.CANCELED;
            case "ERR": return TransactionState.ERROR;
            default:
                /**
                 * If we try to cpmvert am invalid string.
                 */
                throw new InvalidParameterException(code);
        }
    }

}
