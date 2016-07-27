package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 23.09.15.
 */

public enum BusinessTransactionType implements FermatEnum {
    STOCK("STO"),
    SALE("SAL"),
    PURCHASE("PUR");

    private String code;

    BusinessTransactionType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static BusinessTransactionType getByCode(String code) throws InvalidParameterException {
        switch (code) {
            case "STO":
                return BusinessTransactionType.STOCK;
            case "SAL":
                return BusinessTransactionType.SALE;
            case "PUR":
                return BusinessTransactionType.PURCHASE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, new StringBuilder().append("Code Received: ").append(code).toString(), "This Code Is Not Valid for the BusinessTransactionType enum");
        }
    }
}
