package com.bitdubai.fermat_api.layer.all_definition.common.system.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.DeveloperUtils</code>
 * enumerates all the developer utils that we've in fermat.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public enum DeveloperUtils implements FermatEnum {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    DATABASE_TOOLS("DBT"),
    LOG_TOOLS("LGT"),;

    private String code;

    DeveloperUtils(final String code) {

        this.code = code;
    }

    public static DeveloperUtils getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "DBT":
                return DATABASE_TOOLS;
            case "LGT":
                return LOG_TOOLS;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The received code is not valid for the DeveloperUtils enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
