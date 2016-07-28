package com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums;

import java.io.Serializable;

/**
 * Created by natalia on 06/07/15.
 */
public enum BalanceType  implements Serializable {

    AVAILABLE("AVAILABLE"),
    REAL("REAL"),
    BOOK("BOOK");

    private final String code;

    BalanceType(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static BalanceType getByCode(String code) {

        switch (code) {
            case "BOOK":      return BOOK;
            case "REAL":      return REAL;
            case "AVAILABLE": return AVAILABLE;
            default:          return AVAILABLE;
        }
    }
}
