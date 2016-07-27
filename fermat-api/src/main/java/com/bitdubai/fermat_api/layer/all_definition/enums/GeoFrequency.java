package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by nerio on 3/7/2016.
 */
public enum GeoFrequency implements FermatEnum {
    /**
     * Definition types frequency
     */
    LOW("LO", 15000),
    NORMAL("NL", 10000),
    HIGH("HI", 5000),
    NONE("NN", 0),;

    /**
     * Represent the code of the message status
     */
    private final String code;

    private final int refreshInterval;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    GeoFrequency(String code, int refreshInterval) {
        this.code = code;
        this.refreshInterval = refreshInterval;
    }

    /**
     * Throw the method <code>getCode</code> you can get the code of the specific element of the enum.
     *
     * @return the code of the enum.
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return MessagesStatus enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static GeoFrequency getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "NN":
                return GeoFrequency.NONE;
            case "LO":
                return GeoFrequency.LOW;
            case "NL":
                return GeoFrequency.NORMAL;
            case "HI":
                return GeoFrequency.HIGH;
        }

        /**
         * If we try to convert am invalid string.
         */
        throw new InvalidParameterException(code);
    }

    /**
     * This method returns the  pre-defined refresh interval
     *
     * @return
     */
    public int getRefreshInterval() {
        return this.refreshInterval;
    }
}
