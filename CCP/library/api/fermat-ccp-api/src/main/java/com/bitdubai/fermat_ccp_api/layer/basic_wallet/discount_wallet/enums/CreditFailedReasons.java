package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 3/24/15.
 */
public enum CreditFailedReasons {

    //Modified by Manuel Perez on 05/08/2015
    ACCOUNT_ALREADY_LOCKED ("AAL"),
    ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET ("ADNBTTW"),
    ACCOUNT_NOT_OPEN ("ANOTO"),
    CANT_SAVE_TRANSACTION("CST"),
    AMOUNT_MUST_BE_OVER_ZERO("AMBOZ"),
    CRYPTO_AMOUNT_MUST_BE_OVER_ZERO("CAMBOZ");

    private String code;

    CreditFailedReasons (String code) {
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static CreditFailedReasons getByCode(String code)throws InvalidParameterException {

        switch (code){

            case "AAL":
                return CreditFailedReasons.ACCOUNT_ALREADY_LOCKED;
            case "ADNBTTW":
                return CreditFailedReasons.ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET;
            case "ANOTO":
                return CreditFailedReasons.ACCOUNT_NOT_OPEN;
            case "CST":
                return CreditFailedReasons.CANT_SAVE_TRANSACTION;
            case "AMBOZ":
                return CreditFailedReasons.AMOUNT_MUST_BE_OVER_ZERO;
            case "CAMBOZ":
                return CreditFailedReasons.CRYPTO_AMOUNT_MUST_BE_OVER_ZERO;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the CreditFailedReasons enum");


        }

    }
}
