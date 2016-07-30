package com.bitdubai.fermat_cht_api.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by José Vilchez on 01/03/16.
 */
public enum ContactStatus implements FermatEnum {
    /**
     * Definition types
     */
    AVAILABLE("AVA"),
    BLOCKED("BLO");

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    ContactStatus(String code) {
        this.code = code;
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
     * @return ContactStatus enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static ContactStatus getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "AVA":
                return ContactStatus.AVAILABLE;
            case "BLO":
                return ContactStatus.BLOCKED;
        }

        /**
         * If we try to convert an invalid string.
         */
        throw new InvalidParameterException(code);
    }
}
