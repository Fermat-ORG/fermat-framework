package com.bitdubai.fermat_dap_api.layer.dap_wallet.common.enums;

/**
 * Created by franklin on 24/09/15.
 */
public enum TransactionType {

    DEBIT("DEBIT"),
    CREDIT("CREDIT");

    private final String code;

    TransactionType(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static TransactionType getByCode(String code) {

        switch (code) {
            case "DEBIT": return TransactionType.DEBIT;
            case "CREDIT": return TransactionType.CREDIT;
        }

        /**
         * Return by default.
         */
        return TransactionType.CREDIT;
    }
}
