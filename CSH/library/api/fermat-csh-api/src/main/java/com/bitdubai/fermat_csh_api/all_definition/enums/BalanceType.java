package com.bitdubai.fermat_csh_api.all_definition.enums;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
public enum BalanceType {

    //TODO: Verify usefulness. Not needed for transactions (i think)


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
