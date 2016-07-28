package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.InputTransactionType</code>
 * enumerates all the possible types of an input transaction.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 07/03/2016.
 */
public enum InputTransactionType implements FermatEnum {

    /*
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    ORIGINAL("ORI"),
    PARTIAL("PAR"),;

    private final String code;

    InputTransactionType(final String code) {
        this.code = code;
    }

    public static InputTransactionType getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "ORI":
                return ORIGINAL;
            case "PAR":
                return PARTIAL;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The code received is not valid for the InputTransactionType enum."
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
