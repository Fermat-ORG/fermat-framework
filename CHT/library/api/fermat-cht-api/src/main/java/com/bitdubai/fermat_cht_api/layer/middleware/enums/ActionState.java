package com.bitdubai.fermat_cht_api.layer.middleware.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 03/05/16.
 */
public enum ActionState {
    /**
     * Definition types
     */
//    PENDING         ("PEND"),
    ACTIVE("ACTV"),
    NONE("NONE");
//    DONE            ("DONE");

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    ActionState(String code) {
        this.code = code;
    }

    /**
     * Return a string code
     *
     * @return String that represent of the message status
     */
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
    public static ActionState getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "ACTV":
                return ActionState.ACTIVE;
            case "NONE":
                return ActionState.NONE;
        }

        /**
         * If we try to convert an invalid string.
         */
        throw new InvalidParameterException(code);
    }
}
