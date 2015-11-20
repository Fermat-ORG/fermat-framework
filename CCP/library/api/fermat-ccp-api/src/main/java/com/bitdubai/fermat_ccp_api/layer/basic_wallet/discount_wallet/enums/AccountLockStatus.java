package com.bitdubai.fermat_ccp_api.layer.basic_wallet.discount_wallet.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 3/26/15.
 */
public enum AccountLockStatus {

    //Modified by Manuel Perez on 05/08/2015
    LOCKED("LOCKED"),
    UNLOCKED("UNLOCKED");

    private String code;

    AccountLockStatus(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static AccountLockStatus getByCode(String code) throws InvalidParameterException{

        switch (code){

            case "LOCKED":
                return AccountLockStatus.LOCKED;
            case "UNLOCKED":
                return AccountLockStatus.UNLOCKED;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the AccountLockStatus enum");


        }

    }
    
}
