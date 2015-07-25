package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums;

/**
 * Created by Matias Furszyfer on 2015.07.21..
 */
public enum ShowMoneyType {

    BITCOIN (1),
    BITS   (2);

    private final int code;

    ShowMoneyType(int Code) {
        this.code = Code;
    }

    public int getCode()   { return this.code ; }

    public static ShowMoneyType getByCode(int code) {

        switch (code) {
            case 1: return ShowMoneyType.BITCOIN;
            case 2: return ShowMoneyType.BITS;

        }

        /**
         * Return by default.
         */
        return ShowMoneyType.BITCOIN;
    }
}
