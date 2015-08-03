package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Addons {
    ERROR_MANAGER ("ERRM"),
    EVENT_MANAGER ("EVNTM"),
    OS ("Os"),
    LOCAL_DEVICE("LOCD"),
    REMOTE_DEVICE("REMD"),
    DEVICE_USER ("DEVU"),
    LICENSE_MANAGER ("LICM"),
    INTRA_USER("INTU"),
    EXTRA_USER("EXTU"),
    DEVICE_CONNECTIVITY("DEVC"),
    PLATFORM_INFO("PLATINF"),
    LOG_MANAGER("LOGM");


    private final String code;

    Addons(String code) {
        this.code = code;
    }

    public String getCode()   { return this.code; }

    public static Addons getByCode(String code) throws InvalidParameterException {
        switch(code){
            case "Error Manager":
                return Addons.ERROR_MANAGER ;
            case "Event Manager":
                return Addons.EVENT_MANAGER ;
            case "Os":
                return Addons.OS ;
            case "Local Device":
                return Addons.LOCAL_DEVICE;
            case "Remote Device":
                return Addons.REMOTE_DEVICE;
            case "Device user":
                return Addons.DEVICE_USER ;
            case "License Manager":
                return Addons.LICENSE_MANAGER ;
            case "Intra User":
                return Addons.INTRA_USER;
            case "Extra User":
                return Addons.EXTRA_USER;
            case "Device Connectivity":
                return Addons.DEVICE_CONNECTIVITY;
            case "Platform Info":
                return Addons.PLATFORM_INFO;
            case "Log Manager":
                return Addons.LOG_MANAGER;
            //Modified by Manuel Perez on 03/08/2015
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Addons enum");

        }
    }


}
