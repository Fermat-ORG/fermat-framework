package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public enum Addons implements FermatEnum {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    DEVICE_CONNECTIVITY("DEVC"),
    DEVICE_USER("DEVU"),
    ERROR_MANAGER("ERRM"),
    EVENT_MANAGER("EVNTM"),
    LICENSE_MANAGER("LICM"),
    LOCAL_DEVICE("LOCD"),
    LOCATION_MANAGER("LOCMG"),
    LOG_MANAGER("LOGM"),
    OS("OS"),
    PLATFORM_FILE_SYSTEM("PLAFS"),
    PLATFORM_INFO("PLATINF"),
    PLUGIN_FILE_SYSTEM("PLUFS"),
    REMOTE_DEVICE("REMD"),

    ;

    private final String code;

    Addons(final String code) {

        this.code = code;
    }

    public static Addons getByKey(String code) throws InvalidParameterException {

        switch (code) {

            case "DEVC":    return DEVICE_CONNECTIVITY;
            case "DEVU":    return DEVICE_USER;
            case "ERRM":    return ERROR_MANAGER;
            case "EVNTM":   return EVENT_MANAGER;
            case "LICM":    return LICENSE_MANAGER;
            case "LOCD":    return LOCAL_DEVICE;
            case "LOCMG":   return LOCATION_MANAGER;
            case "LOGM":    return LOG_MANAGER;
            case "OS":      return OS;
            case "PLAFS":   return PLATFORM_FILE_SYSTEM;
            case "PLATINF": return PLATFORM_INFO;
            case "PLUFS":   return PLUGIN_FILE_SYSTEM;
            case "REMD":    return REMOTE_DEVICE;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This Code Is Not Valid for the Addons enum"
                );

        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
