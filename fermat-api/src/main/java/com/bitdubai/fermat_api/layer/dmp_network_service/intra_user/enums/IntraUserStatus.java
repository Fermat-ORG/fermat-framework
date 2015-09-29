package com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 */
public enum IntraUserStatus {
    //Modified by Manuel Perez on 05/08/2015
    UNKNOWN("UNK"),
    LOGGED_IN("LOGIN"),
    LOGGED_OUT("LOGOUT");

    private String code;

    IntraUserStatus(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static IntraUserStatus getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "UNK":
                return IntraUserStatus.UNKNOWN;
            case "LOGIN":
                return IntraUserStatus.LOGGED_IN;
            case "LOGOUT":
                return IntraUserStatus.LOGGED_OUT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the IntraUserStatus enum");

        }

    }

}
