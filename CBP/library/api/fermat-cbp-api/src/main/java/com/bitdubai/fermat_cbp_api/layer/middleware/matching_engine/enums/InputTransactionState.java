package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.InputTransactionState</code>
 * enumerates all the possible types of an input transaction.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 */
public enum InputTransactionState implements FermatEnum {

    /*
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    MATCHED("MAT"),
    SPLIT("SPL"),
    UNMATCHED("UNM"),;

    private final String code;

    InputTransactionState(final String code) {
        this.code = code;
    }

    public static InputTransactionState getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "MAT":
                return MATCHED;
            case "SPL":
                return SPLIT;
            case "UNM":
                return UNMATCHED;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The code received is not valid for the InputTransactionState enum."
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
