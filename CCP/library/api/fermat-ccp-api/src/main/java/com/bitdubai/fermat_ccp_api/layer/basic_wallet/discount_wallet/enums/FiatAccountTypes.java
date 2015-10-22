package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/15/15.
 */
public enum FiatAccountTypes {

    //Modified by Manuel Perez on 05/08/2015
    SAVINGS_ACCOUNT("SAVA"),
    CURRENT_ACCOUNT("CURA");

    private String code;

    FiatAccountTypes(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static FiatAccountTypes getByCode(String code) throws InvalidParameterException{

        switch (code){

            case "SAVA":
                return FiatAccountTypes.SAVINGS_ACCOUNT;
            case "CURA":
                return FiatAccountTypes.CURRENT_ACCOUNT;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the FiatAccountTypes enum");

        }

    }

}
