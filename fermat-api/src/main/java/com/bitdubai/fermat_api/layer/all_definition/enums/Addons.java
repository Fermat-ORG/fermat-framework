package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Addons {
    ERROR_MANAGER ("Error Manager"),
    EVENT_MANAGER ("Event Manager"),
    OS ("Os"),
    LOCAL_DEVICE("Local Device"),
    REMOTE_DEVICE("Remote Device"),
    DEVICE_USER ("Device user"),
    LICENSE_MANAGER ("License Manager"),
    INTRA_USER("Intra User"),
    EXTRA_USER("Extra User"),
    DEVICE_CONNECTIVITY("Device Connectivity"),
    LOG_MANAGER("Log Manager");

    public static Addons getByKey(String key) throws InvalidParameterException {
        switch(key){
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
            case "Log Manager":
                return Addons.LOG_MANAGER;
        }
        throw new InvalidParameterException(key);
    }

    private final String key;

    Addons(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }
}
