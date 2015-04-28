package com.bitdubai.fermat_api.layer._12_basic_wallet.discount_wallet.enums;

/**
 * Created by ciencias on 3/26/15.
 */
public enum CryptoValueChunkStatus {


    UNSPENT ("USP"),
    SPENT   ("SPE"),
    FATHER ("FTH"),
    LOCKED ("LCK");

    private final String code;

    CryptoValueChunkStatus(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code ; }

    public static CryptoValueChunkStatus getByCode(String code) {

        switch (code) {
            case "USP": return CryptoValueChunkStatus.UNSPENT;
            case "SPE": return CryptoValueChunkStatus.SPENT;
            case "DEA": return CryptoValueChunkStatus.FATHER;
            case "LCK": return CryptoValueChunkStatus.LOCKED;
        }

        /**
         * Return by default.
         */
        return CryptoValueChunkStatus.LOCKED;
    }
}
