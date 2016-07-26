package com.bitdubai.fermat_cbp_api.all_definition.enums;

//import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
public enum TransactionType implements FermatEnum {

    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String code;

    TransactionType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static TransactionType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "DEBIT":
                return TransactionType.DEBIT;
            case "CREDIT":
                return TransactionType.CREDIT;
            default:
                return TransactionType.CREDIT;
        }
    }
}
