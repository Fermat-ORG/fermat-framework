package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;


/**
 * The enum <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningTransactionState</code>
 * enumerates all the possible types of an earning transaction.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 */
public enum EarningTransactionState implements FermatEnum {

    /*
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    CALCULATED("CAL"),
    EXTRACTED("EXT");

    private final String code;

    EarningTransactionState(final String code) {
        this.code = code;
    }

    public static EarningTransactionState getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "CAL":
                return CALCULATED;
            case "EXT":
                return EXTRACTED;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The code received is not valid for the EarningTransactionState enum."
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
