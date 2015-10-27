package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Addons implements FermatEnum {

    ERROR_MANAGER("ERRM"),
    EVENT_MANAGER("EVNTM"),
    LOCATION_MANAGER("LOCMG"),
    OS("OS"),
    LOCAL_DEVICE("LOCD"),
    REMOTE_DEVICE("REMD"),
    DEVICE_USER("DEVU"),
    LICENSE_MANAGER("LICM"),
    INTRA_USER("INTU"),
    EXTRA_USER("EXTU"),
    DEVICE_CONNECTIVITY("DEVC"),
    PLATFORM_INFO("PLATINF"),
    LOG_MANAGER("LOGM");


    private String code;

    Addons(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

    public static Addons getByKey(String code) throws InvalidParameterException {
        switch (code) {
            case "ERRM":    return ERROR_MANAGER;
            case "EVNTM":   return EVENT_MANAGER;
            case "OS":      return OS;
            case "LOCD":    return LOCAL_DEVICE;
            case "REMD":    return REMOTE_DEVICE;
            case "DEVU":    return DEVICE_USER;
            case "LICM":    return LICENSE_MANAGER;
            case "INTU":    return INTRA_USER;
            case "EXTU":    return EXTRA_USER;
            case "DEVC":    return DEVICE_CONNECTIVITY;
            case "PLATINF": return PLATFORM_INFO;
            case "LOGM":    return LOG_MANAGER;
            case "LOCMG":   return LOCATION_MANAGER;
            //Modified by Manuel Perez on 03/08/2015
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + code, "This Code Is Not Valid for the Addons enum");

        }
    }


}
