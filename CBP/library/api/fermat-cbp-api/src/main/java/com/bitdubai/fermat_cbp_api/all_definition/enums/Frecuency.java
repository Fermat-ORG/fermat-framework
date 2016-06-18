package com.bitdubai.fermat_cbp_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 01/04/16.
 */
public enum Frecuency implements FermatEnum {
    /**
     * Definition types frecuency
     */
    LOW    ("LOW"),
    NORMAL ("NORMAL"),
    HIGH   ("HIGH"),
    NONE   ("NONE");

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    Frecuency(String code) {
        this.code = code;
    }
    /**
     * Throw the method <code>getCode</code> you can get the code of the specific element of the enum.
     *
     * @return the code of the enum.
     */
    @Override
    public String getCode() {
        return this.code ;
    }

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return MessagesStatus enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static Frecuency getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "LOW":
                return Frecuency.LOW;
            case "NORMAL":
                return Frecuency.NORMAL;
            case "HIGH":
                return Frecuency.HIGH;
            case "NONE":
                return Frecuency.NONE;
        }

        /**
         * If we try to convert am invalid string.
         */
        throw new InvalidParameterException(code);
    }
}
