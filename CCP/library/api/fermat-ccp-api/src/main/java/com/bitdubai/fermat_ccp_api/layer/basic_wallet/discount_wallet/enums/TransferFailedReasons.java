package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 3/24/15.
 */
public enum TransferFailedReasons {

    //Modified by Manuel Perez on 05/08/2015
    ACCOUNT_FROM_ALREADY_LOCKED ("AFAL"),
    ACCOUNT_TO_ALREADY_LOCKED ("ATAL"),
    ACCOUNT_FROM_DOES_NOT_BELONG_TO_THIS_WALLET ("AFDNBTTW"),
    ACCOUNT_TO_DOES_NOT_BELONG_TO_THIS_WALLET ("ATDNBTTW"),
    ACCOUNT_FROM_NOT_OPEN ("AFNO"),
    ACCOUNT_TO_NOT_OPEN ("ATNO"),
    NOT_ENOUGH_FUNDS ("NEF"),
    CANT_SAVE_TRANSACTION("CST"),
    DATABASE_UNAVAILABLE("DATAAVA"),
    FIAT_CURRENCY_FROM_AND_FIAT_CURRENCY_TO_DONT_MATCH("FCFAFCTDM");

    private String code;
    
    TransferFailedReasons (String code) {
        this.code = code;
    }
    
    public String getCode(){
        return this.code;
    }

    public static TransferFailedReasons getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "AFAL":
                return TransferFailedReasons.ACCOUNT_FROM_ALREADY_LOCKED;
            case "ATAL":
                return TransferFailedReasons.ACCOUNT_TO_ALREADY_LOCKED;
            case "AFDNBTTW":
                return TransferFailedReasons.ACCOUNT_FROM_DOES_NOT_BELONG_TO_THIS_WALLET;
            case "ATDNBTTW":
                return TransferFailedReasons.ACCOUNT_TO_DOES_NOT_BELONG_TO_THIS_WALLET;
            case "AFNO":
                return TransferFailedReasons.ACCOUNT_FROM_NOT_OPEN;
            case "ATNO":
                return TransferFailedReasons.ACCOUNT_TO_NOT_OPEN;
            case "NEF":
                return TransferFailedReasons.NOT_ENOUGH_FUNDS;
            case "CST":
                return TransferFailedReasons.CANT_SAVE_TRANSACTION;
            case "DATAAVA":
                return TransferFailedReasons.DATABASE_UNAVAILABLE;
            case "FCFAFCTDM":
                return TransferFailedReasons.FIAT_CURRENCY_FROM_AND_FIAT_CURRENCY_TO_DONT_MATCH;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TransferFailedReasons enum");

        }

    }
}
