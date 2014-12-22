package com.bitdubai.smartwallet.core.enums;

/**
 * Created by ciencias on 20.12.14.
 */
public enum CryptoCurrency {
    BITCOIN ("BTC"),
    LITECOIN   ("LTC");

    private final String mCode;

    CryptoCurrency(String Code) {
        this.mCode = Code;
    }

    public String Code()   { return mCode; }

}