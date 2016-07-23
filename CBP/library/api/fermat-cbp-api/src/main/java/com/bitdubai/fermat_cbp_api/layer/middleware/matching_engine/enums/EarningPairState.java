package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.enums.EarningPairState</code>
 * enumerates all the possible types of an earning pair.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 */
public enum EarningPairState implements FermatEnum {

    /*
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    ASSOCIATED("ASS"),
    DISASSOCIATED("DIS"),;

    private final String code;

    EarningPairState(final String code) {
        this.code = code;
    }

    public static EarningPairState getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "ASS":
                return ASSOCIATED;
            case "DIS":
                return DISASSOCIATED;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The code received is not valid for the EarningPairState enum."
                );
        }
    }

    @Override
    public final String getCode() {
        return this.code;
    }

}
