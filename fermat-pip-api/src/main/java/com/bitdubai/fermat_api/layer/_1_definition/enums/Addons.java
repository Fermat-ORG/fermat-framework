package com.bitdubai.fermat_api.layer._1_definition.enums;

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
    DEVICE_CONNECTIVITY("Device Connectivity");


    private final String key;

    Addons(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }
}
