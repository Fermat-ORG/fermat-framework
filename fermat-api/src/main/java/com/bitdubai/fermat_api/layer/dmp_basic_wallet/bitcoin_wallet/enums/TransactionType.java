package com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.enums;

/**
 * Created by eze on 2015.06.17..
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
