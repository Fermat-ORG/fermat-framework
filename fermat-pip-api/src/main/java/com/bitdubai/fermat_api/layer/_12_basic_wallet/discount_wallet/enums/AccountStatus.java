package com.bitdubai.fermat_api.layer._12_basic_wallet.discount_wallet.enums;

/**
 * Created by ciencias on 3/24/15.
 */
public enum AccountStatus  {

    CREATED ("CRE"),
    OPEN ("OPN"),
    CLOSED   ("CLO"),
    DELETED ("DEL");

    private final String code;

    AccountStatus(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static AccountStatus getByCode(String code) {

        switch (code) {
            case "CRE": return AccountStatus.CREATED;
            case "OPN": return AccountStatus.OPEN;
            case "CLO": return AccountStatus.CLOSED;
            case "DEL": return AccountStatus.DELETED;
        }

        /**
         * Return by default.
         */
        return AccountStatus.CREATED;
    }
}