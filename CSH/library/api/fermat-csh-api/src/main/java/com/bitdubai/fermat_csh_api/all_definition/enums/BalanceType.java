package com.bitdubai.fermat_csh_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
public enum BalanceType {

    AVAILABLE("AVAILABLE"),
    BOOK("BOOK"),
    ALL("ALL");
    ;

    private final String code;

    BalanceType(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static BalanceType getByCode(String code) throws InvalidParameterException{

        switch (code) {
            case "AVAILABLE": return AVAILABLE;
            case "BOOK":      return BOOK;
            case "ALL":       return ALL;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the BalanceType enum");
        }
    }
}
