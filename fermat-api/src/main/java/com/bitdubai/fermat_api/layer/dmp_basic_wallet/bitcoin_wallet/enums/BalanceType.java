package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums;

/**
 * Created by natalia on 06/07/15.
 */
public enum BalanceType {
    AVILABLE("AVILABLE"),
    BOOK("BOOK");

    private final String code;

    BalanceType(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static BalanceType getByCode(String code) {

        switch (code) {
            case "BOOK": return BalanceType.BOOK;
            case "AVILABLE": return BalanceType.AVILABLE;

        }

        /**
         * Return by default.
         */
        return BalanceType.AVILABLE;
    }
}
