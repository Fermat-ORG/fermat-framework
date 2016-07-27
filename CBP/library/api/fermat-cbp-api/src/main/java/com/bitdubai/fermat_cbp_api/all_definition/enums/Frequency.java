package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 01/04/16.
 */
public enum Frequency implements FermatEnum {
    /**
     * Definition types frecuency
     */
    LOW("LO", 1000),
    NORMAL("NL", 2000),
    HIGH("HI", 3000),
    NONE("NN", 0);

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
    Frequency(String code, int refreshInterval) {
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
    public static Frequency getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "LO":
                return Frequency.LOW;
            case "NL":
                return Frequency.NORMAL;
            case "HI":
                return Frequency.HIGH;
            case "NN":
                return Frequency.NONE;
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
