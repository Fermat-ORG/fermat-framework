package com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 21/10/2015.
 */
public enum IntraUserStatus implements FermatEnum {

    LOGGED_IN("LOGIN"),
    LOGGED_OUT("LOGOUT"),
    UNKNOWN("UNK"),

    ;

    private final String code;

    IntraUserStatus(final String code){

        this.code = code;
    }


    public static IntraUserStatus getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "LOGIN":  return LOGGED_IN ;
            case "LOGOUT": return LOGGED_OUT;
            case "UNK":    return UNKNOWN   ;

            default:
                throw new InvalidParameterException(
                        "Code received: " + code,
                        "The code received is not valid for the IntraUserStatus enum."
                );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }

}
