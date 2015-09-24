package com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by eze on 2015.08.01..
 */
public enum IntraUserNotificationDescriptor {
    //Modified by Manuel Perez on 05/08/2015
    ASKFORACCEPTANCE("ASK"),
    ACCEPTED("ACP"),
    CANCEL("CAN"),
    DISCONNECTED("DIS"),
    RECEIVED("REC"),
    DENIED("DEN");

    private String code;

    IntraUserNotificationDescriptor(String code){

        this.code=code;

    }

    public String getCode(){

        return this.code;

    }

    public static IntraUserNotificationDescriptor getByCode(String code)throws InvalidParameterException{

        switch (code){

            case "ASK":
                return IntraUserNotificationDescriptor.ASKFORACCEPTANCE;
            case "CAN":
                return IntraUserNotificationDescriptor.CANCEL;
            case "ACP":
                return IntraUserNotificationDescriptor.ACCEPTED;
            case "DIS":
                return IntraUserNotificationDescriptor.DISCONNECTED;
            case "REC":
                return IntraUserNotificationDescriptor.RECEIVED;
            case "DEN":
                return IntraUserNotificationDescriptor.DENIED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the IntraUserNotificationDescriptor enum");


        }

    }

}
