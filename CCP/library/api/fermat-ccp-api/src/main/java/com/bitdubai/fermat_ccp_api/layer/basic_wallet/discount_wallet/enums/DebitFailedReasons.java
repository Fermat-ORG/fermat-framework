package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 3/24/15.
 */
public enum DebitFailedReasons  {

    //Modified by Manuel Perez on 05/08/2015
    ACCOUNT_ALREADY_LOCKED ("AAL"),
    ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET ("ADNBTTW"),
    ACCOUNT_NOT_OPEN ("ANOTO"),
    ACCOUNT_WITH_NOT_ENOUGH_FUNDS ("AWNEF"),
    CANT_SAVE_TRANSACTION("CST"),
    CANT_CALCULATE_AVAILABLE_AMOUNT_OF_FIAT_MONEY("CCAAOFM"),
    NOT_ENOUGH_FIAT_AVAILABLE("NEFA"),
    VALUE_CHUNKS_TABLE_FAIL_TO_LOAD_TO_MEMORY("VCTFTLTM"),
    AMOUNT_MUST_BE_OVER_ZERO("AMBOZ"),
    CRYPTO_AMOUNT_MUST_BE_OVER_ZERO("CAMBOZ");

    private String code;

    DebitFailedReasons (String code) {
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static DebitFailedReasons getByCode(String code) throws InvalidParameterException {

        switch(code){

            case "AAL":
                return DebitFailedReasons.ACCOUNT_ALREADY_LOCKED;
            case "ADNBTTW":
                return DebitFailedReasons.ACCOUNT_DOES_NOT_BELONG_TO_THIS_WALLET;
            case "ANOTO":
                return DebitFailedReasons.ACCOUNT_NOT_OPEN;
            case "AWNEF":
                return DebitFailedReasons.ACCOUNT_WITH_NOT_ENOUGH_FUNDS;
            case "CST":
                return DebitFailedReasons.CANT_SAVE_TRANSACTION;
            case "CCAAOFM":
                return DebitFailedReasons.CANT_CALCULATE_AVAILABLE_AMOUNT_OF_FIAT_MONEY;
            case "NEFA":
                return DebitFailedReasons.NOT_ENOUGH_FIAT_AVAILABLE;
            case "VCTFTLTM":
                return DebitFailedReasons.VALUE_CHUNKS_TABLE_FAIL_TO_LOAD_TO_MEMORY;
            case "AMBOZ":
                return DebitFailedReasons.AMOUNT_MUST_BE_OVER_ZERO;
            case "CAMBOZ":
                return DebitFailedReasons.CRYPTO_AMOUNT_MUST_BE_OVER_ZERO;
            //Modified by Manuel Perez on 05/08/2015
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the DebitFailedReasons enum");

        }


    }
}
