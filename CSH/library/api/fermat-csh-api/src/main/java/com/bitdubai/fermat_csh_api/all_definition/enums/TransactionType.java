package com.bitdubai.fermat_csh_api.all_definition.enums;

import com.bitdubai.fermat_csh_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
public enum TransactionType {
    //TODO: Verify usefulness. Not needed for transactions (i think)

    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String code;

    TransactionType(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static TransactionType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DEBIT": return TransactionType.DEBIT;
            case "CREDIT": return TransactionType.CREDIT;
            default: return TransactionType.CREDIT;
        }
    }
}
