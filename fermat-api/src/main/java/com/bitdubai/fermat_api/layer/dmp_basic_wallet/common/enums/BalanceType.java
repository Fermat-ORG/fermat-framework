package com.bitdubai.fermat_api.layer.dmp_basic_wallet.common.enums;

/**
 * Created by natalia on 06/07/15.
 */
public enum BalanceType {

    AVAILABLE("AVAILABLE"),
    BOOK("BOOK");

    private final String code;

    BalanceType(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static BalanceType getByCode(String code) {

        switch (code) {
            case "BOOK":      return BOOK;
            case "AVAILABLE": return AVAILABLE;
            default:          return AVAILABLE;
        }
    }
}
