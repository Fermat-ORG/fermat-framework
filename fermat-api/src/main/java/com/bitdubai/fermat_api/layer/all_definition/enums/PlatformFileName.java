package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 22.01.15.
 */
public enum PlatformFileName {
    //Modified by Manuel Perez on 03/08/2015
    LAST_STATE ("Platform_Last_State");

    private String code;

    PlatformFileName(String code) {
        this.code = code;
    }

    public String getCode()   { return code; }

    public static PlatformFileName getByCode(String code) throws InvalidParameterException{

        switch(code){

            case "Platform_Last_State":
                return PlatformFileName.LAST_STATE;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the PlatformFileName enum");


        }

    }

}
