package com.bitdubai.fermat_api.layer.ccp_network_service.template;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 */
public enum TemplateStatus {
    //Modified by Manuel Perez on 05/08/2015
    UNKNOWN("UNK"),
    LOGGED_IN("LOGIN"),
    LOGGED_OUT("LOGOUT");

    private String code;

    TemplateStatus(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static TemplateStatus getByCode(String code)throws InvalidParameterException {

        switch (code){

            case "UNK":
                return TemplateStatus.UNKNOWN;
            case "LOGIN":
                return TemplateStatus.LOGGED_IN;
            case "LOGOUT":
                return TemplateStatus.LOGGED_OUT;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the TemplateStatus enum");

        }

    }
}
