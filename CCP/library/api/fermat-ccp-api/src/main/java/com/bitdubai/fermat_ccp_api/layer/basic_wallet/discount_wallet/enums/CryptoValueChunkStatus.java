package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

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

    public static CryptoValueChunkStatus getByCode(String code)throws InvalidParameterException {

        switch (code) {
            case "USP": return CryptoValueChunkStatus.UNSPENT;
            case "SPE": return CryptoValueChunkStatus.SPENT;
            case "DEA": return CryptoValueChunkStatus.FATHER;
            case "LCK": return CryptoValueChunkStatus.LOCKED;
            //Modified by Manuel Perez on 05/08/2015
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CryptoValueChunkStatus enum");

        }

        /**
         * Return by default.
         */
        //return CryptoValueChunkStatus.LOCKED;
    }
}
