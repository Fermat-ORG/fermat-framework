package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.06.17..
 */
public enum TransactionState {
    NEW("NEW"),
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
            case "PIW": return TransactionState.PERSISTED_IN_WALLET;
            case "STCV": return TransactionState.SENT_TO_CRYPTO_VOULT;
            case "SS": return TransactionState.SUCCESSFUL_SENT;
            case "RCV": return TransactionState.RECEIVED;
        }
        /**
         * If we try to cpmvert am invalid string.
         */
        throw new InvalidParameterException(code);
    }}
