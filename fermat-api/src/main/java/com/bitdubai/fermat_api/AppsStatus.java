package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * Created by Matias Furszyfer on 2016.02.10..
 */
public enum AppsStatus implements FermatEnum{

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    RELEASE  ("r",1),
    BETA    ("b",2),
    ALPHA    ("a",3),
    DEV ("d",4);

    /**
     * sets the default network that will be used at start up.
     */
    private static final AppsStatus DEFAULT_STATUS = AppsStatus.RELEASE;

    private final String code;
    private final int number;

    AppsStatus(final String code,final int number) {
        this.code = code;
        this.number = number;
    }

    public static AppsStatus getByCode(String code) {

        switch (code) {
            case "r":  return RELEASE;
            case "b": return BETA;
            case "a": return ALPHA;
            case "d": return DEV;
            default:      return DEFAULT_STATUS;
        }
    }

    public int getNumber() {
        return number;
    }

    public boolean isAppStatusAvailable(AppsStatus appsStatus){
        return (appsStatus!=null) ?appsStatus.getNumber()>=this.number : false;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Gets the default network type selected for this platform.
     * @return the default BlockchainNetworkType
     */
    public static AppsStatus getDefaultStatus(){
        return DEFAULT_STATUS;
    }
}
