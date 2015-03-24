package com.bitdubai.fermat_api.layer._1_definition.enums;

/**
 * Created by ciencias on 20.12.14.
 */
public enum CryptoCurrency {
    BITCOIN ("BTC"),
    LITECOIN   ("LTC");

    private final String code;

    CryptoCurrency(String Code) {
        this.code = Code;
    }

    public String getCode()   { return this.code ; }

    public static CryptoCurrency getByCode(String code) {

        switch (code) {
            case "BTC": return CryptoCurrency.BITCOIN;
            case "LTC": return CryptoCurrency.LITECOIN;
        }

        /**
         * Return by default.
         */
        return CryptoCurrency.BITCOIN;
    }
}