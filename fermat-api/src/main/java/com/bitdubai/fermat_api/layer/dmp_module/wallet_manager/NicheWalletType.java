package com.bitdubai.fermat_api.layer.dmp_module.wallet_manager;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 26.01.15.
 */
public enum NicheWalletType {

    DEFAULT ("DEF"),
    AGE_KIDS_ALL ("AKA"),
    AGE_TEEN_ALL ("ATA"),
    AGE_YOUNG_ALL ("AYA"),
    AGE_ADULT_ALL ("ADA"),
    AGE_SENIOR_ALL ("ASA");

    private String code;

    NicheWalletType(String code) {
        this.code = code;
    }

    public String getCode()   { return code; }

    public static NicheWalletType getByCode (String code) throws InvalidParameterException {

        switch (code)
        {
            case "AKA": return NicheWalletType.AGE_KIDS_ALL;
            case "ATA": return NicheWalletType.AGE_TEEN_ALL;
            case "AYA": return NicheWalletType.AGE_YOUNG_ALL;
            case "ADA": return NicheWalletType.AGE_ADULT_ALL;
            case "ASA": return NicheWalletType.AGE_SENIOR_ALL;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the NicheWallet enum");

        }


        //return DEFAULT;

    }

}
