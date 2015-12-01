package com.bitdubai.fermat_bnk_api.all_definition.enums;


import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
public enum TransactionType {

    DEBIT("DEBIT"),
    CREDIT("CREDIT"),
    HOLD("HOLD"),
    UNHOLD("UNHOLD"),;

    private final String code;

    TransactionType(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static TransactionType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DEBIT": return TransactionType.DEBIT;
            case "CREDIT": return TransactionType.CREDIT;
            case "HOLD": return TransactionType.HOLD;
            case "UNHOLD": return TransactionType.UNHOLD;
            default: return TransactionType.CREDIT;
        }
    }
}
