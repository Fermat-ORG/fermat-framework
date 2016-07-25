package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.Addons</code>
 * Contains the different addons available on Fermat.
 * <p/>
 * Created by ciencias on 2/13/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 * Modified by pmgesualdi - (pmgesualdi@hotmail.com) on 30/11/2015.
 */
public enum Addons implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    DEVICE_CONNECTIVITY("DEVC"),
    DEVICE_LOCATION("DEVLOC"),
    DEVICE_USER("DEVU"),
    ERROR_MANAGER("ERRM"),
    EVENT_MANAGER("EVNTM"),
    LICENSE_MANAGER("LICM"),
    LOCAL_DEVICE("LOCD"),
    LOCATION_MANAGER("LOCMG"),
    LOG_MANAGER("LOGM"),
    OS("OS"),
    PLATFORM_DATABASE_SYSTEM("PLADS"),
    PLATFORM_FILE_SYSTEM("PLAFS"),
    PLATFORM_INFO("PLATINF"),
    PLUGIN_FILE_SYSTEM("PLUFS"),
    PLUGIN_DATABASE_SYSTEM("PLUDS"),
    REMOTE_DEVICE("REMD"),

    PLUGIN_BROADCASTER_SYSTEM("PBS"),
    HARDWARE("H");

    private final String code;

    Addons(final String code) {
        this.code = code;
    }

    public static Addons getByKey(String code) throws InvalidParameterException {

        switch (code) {

            case "DEVC":
                return DEVICE_CONNECTIVITY;
            case "DEVLOC":
                return DEVICE_LOCATION;
            case "DEVU":
                return DEVICE_USER;
            case "ERRM":
                return ERROR_MANAGER;
            case "EVNTM":
                return EVENT_MANAGER;
            case "LICM":
                return LICENSE_MANAGER;
            case "LOCD":
                return LOCAL_DEVICE;
            case "LOCMG":
                return LOCATION_MANAGER;
            case "LOGM":
                return LOG_MANAGER;
            case "OS":
                return OS;
            case "PLADS":
                return PLATFORM_DATABASE_SYSTEM;
            case "PLAFS":
                return PLATFORM_FILE_SYSTEM;
            case "PLATINF":
                return PLATFORM_INFO;
            case "PLUDS":
                return PLUGIN_DATABASE_SYSTEM;
            case "PLUFS":
                return PLUGIN_FILE_SYSTEM;
            case "REMD":
                return REMOTE_DEVICE;
            case "PBS":
                return PLUGIN_BROADCASTER_SYSTEM;
            case "H":
                return HARDWARE;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the Addons enum"
                );

        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
