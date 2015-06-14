package com.bitdubai.fermat_api.layer._1_definition.enums;

/**
 * Created by ciencias on 25.01.15.
 */
public enum DeviceDirectory {
    PLATFORM ("com/bitdubai/fermat_api"),
    LOCAL_USERS   ("localusers"),
    LOCAL_WALLETS   ("localwallets");

    private final String name;

    DeviceDirectory(String name) {
        this.name = name;
    }

    public String getName ()   { return this.name; }

}