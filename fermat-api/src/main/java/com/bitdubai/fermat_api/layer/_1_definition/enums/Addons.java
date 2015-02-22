package com.bitdubai.fermat_api.layer._1_definition.enums;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Addons {
    ERROR_MANAGER ("Error Manager"),
    EVENT_MANAGER ("Event Manager"),
    OS ("Os"),
    USER_MANAGER ("User Manager"),
    LICENSE_MANAGER ("License Manager");

    private final String key;

    Addons(String key) {
        this.key = key;
    }

    public String getKey()   { return this.key; }
}
