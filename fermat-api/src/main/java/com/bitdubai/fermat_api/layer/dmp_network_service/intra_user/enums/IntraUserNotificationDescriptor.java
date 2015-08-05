package com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.08.01..
 */
public enum IntraUserNotificationDescriptor {
    //Modified by Manuel Perez on 05/08/2015
    ACCEPTED_YOU("AY"),
    DELETED_YOU("DY"),
    REJECTED_YOU("RY");

    private String code;

    IntraUserNotificationDescriptor(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static IntraUserNotificationDescriptor getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "AY":
                return IntraUserNotificationDescriptor.ACCEPTED_YOU;
            case "DY":
                return IntraUserNotificationDescriptor.DELETED_YOU;
            case "RY":
                return IntraUserNotificationDescriptor.REJECTED_YOU;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the IntraUserNotificationDescriptor enum");


        }

    }

}
