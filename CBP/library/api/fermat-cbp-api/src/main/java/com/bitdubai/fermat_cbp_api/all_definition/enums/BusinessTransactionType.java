package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_cbp_api.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 23.09.15.
 */
 
public enum BusinessTransactionType {
    STOCK("STO"),
    SALE("SAL"),
    PURCHASE("PUR");

    private String code;

    BusinessTransactionType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static BusinessTransactionType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "STO": return BusinessTransactionType.STOCK;
            case "SAL": return BusinessTransactionType.SALE;
            case "PUR": return BusinessTransactionType.PURCHASE;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BusinessTransactionType enum");
        }
    }
}
