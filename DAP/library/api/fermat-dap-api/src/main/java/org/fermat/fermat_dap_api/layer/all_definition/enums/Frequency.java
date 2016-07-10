package org.fermat.fermat_dap_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by franklin on 01/04/16.
 */
public enum Frequency implements FermatEnum {
    /**
     * Definition types frequency
     */
    NONE("NONE"),
    LOW("LOW"),
    NORMAL("NORMAL"),
    HIGH("HIGH"),;

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    Frequency(String code) {
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
     * @return MessagesStatus enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static Frequency getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "NONE":
                return Frequency.NONE;
            case "LOW":
                return Frequency.LOW;
            case "NORMAL":
                return Frequency.NORMAL;
            case "HIGH":
                return Frequency.HIGH;
        }

        /**
         * If we try to convert am invalid string.
         */
        throw new InvalidParameterException(code);
    }
}
