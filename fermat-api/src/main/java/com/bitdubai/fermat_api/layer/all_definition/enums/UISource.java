package com.bitdubai.fermat_api.layer.all_definition.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by Matias Furszyfer  on 2015.07.22..
 */
public enum UISource implements FermatEnum {

    // TODO MAKE THIS COMPATIBLE WITH OTHER TYPES OF OS - ACTIVITIES, TASKS, VIEWS, ADAPTERS ONLY BELONGS TO ANDROID.

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    ACTIVITY("ACTIV"),
    ADAPTER("ADAPT"),
    TASK("TASK"),
    VIEW("VIEW"),;

    private final String code;

    UISource(final String code) {
        this.code = code;
    }


    public static UISource getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "ACTIV":
                return ACTIVITY;
            case "ADAPT":
                return ADAPTER;
            case "TASK":
                return TASK;
            case "VIEW":
                return VIEW;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "This Code Is Not Valid for the UISource enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
