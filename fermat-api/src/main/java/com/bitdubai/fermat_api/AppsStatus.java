package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by Matias Furszyfer on 2016.02.10..
 */
public enum AppsStatus implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    RELEASE("Release", 1),
    BETA("Beta", 2),
    ALPHA("Alpha", 3),
    DEV("Develop", 4);

    /**
     * sets the default network that will be used at start up.
     */
    private static final AppsStatus DEFAULT_STATUS = AppsStatus.RELEASE;

    private final String code;
    private final int number;

    AppsStatus(final String code, final int number) {
        this.code = code;
        this.number = number;
    }

    public static AppsStatus getByCode(String code) {

        switch (code) {
            case "Release":
                return RELEASE;
            case "Beta":
                return BETA;
            case "Alpha":
                return ALPHA;
            case "Develop":
                return DEV;
            default:
                return DEFAULT_STATUS;
        }
    }

    public int getNumber() {
        return number;
    }

    public boolean isAppStatusAvailable(AppsStatus appsStatus) {
        return (appsStatus != null) && appsStatus.getNumber() == this.number;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Gets the default network type selected for this platform.
     *
     * @return the default BlockchainNetworkType
     */
    public static AppsStatus getDefaultStatus() {
        return DEFAULT_STATUS;
    }
}
