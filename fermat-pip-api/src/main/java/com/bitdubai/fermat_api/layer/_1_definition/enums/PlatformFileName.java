package com.bitdubai.fermat_api.layer._1_definition.enums;

/**
 * Created by ciencias on 22.01.15.
 */
public enum PlatformFileName {
    LAST_STATE ("Platform_Last_State");

    private final String mFileName;

    PlatformFileName(String fileName) {
        this.mFileName = fileName;
    }

    public String getFileName()   { return mFileName; }
}
