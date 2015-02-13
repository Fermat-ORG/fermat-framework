package com.bitdubai.wallet_platform_api.layer._1_definition.enums;

/**
 * Created by ciencias on 2/13/15.
 */
public enum Addons {
    ERROR_MANAGER (201),
    EVENT_MANAGER (202),
    OS (301),
    USER_MANAGER (401),
    LICENSE_MANAGER (501);

    private final int index;

    Addons(int index) {
        this.index = index;
    }

    public int getIndex()   { return this.index; }
}
