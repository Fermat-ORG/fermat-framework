package com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/14/15.
 */
public enum Apps {

    //Modified by Manuel Perez on 05/08/2015
    CRYPTO_WALLET_PLATFORM("CWP");

    private String code;

    Apps(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static Apps getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "CWP":
                return Apps.CRYPTO_WALLET_PLATFORM;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Apps enum");


        }

    }
    
}
