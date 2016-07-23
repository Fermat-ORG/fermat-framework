package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by Yordin Alayn on 24.09.15.
 */
public enum BalanceType implements FermatEnum {
    AVAILABLE("AVAILABLE"),
    BOOK("BOOK");

    private final String code;

    BalanceType(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public static BalanceType getByCode(String code) {

        switch (code) {
            case "BOOK":
                return BOOK;
            case "AVAILABLE":
                return AVAILABLE;
            default:
                return AVAILABLE;
        }
    }
}
