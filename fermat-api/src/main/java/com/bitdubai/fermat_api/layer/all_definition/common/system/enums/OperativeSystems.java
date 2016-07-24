package com.bitdubai.fermat_api.layer.all_definition.common.system.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.all_definition.enums.DeveloperUtils</code>
 * enumerates all the operative systems that we can use with fermat.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 27/10/2015.
 */
public enum OperativeSystems implements FermatEnum {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    ANDROID("AND"),
    INDIFFERENT("IND"),;

    private String code;

    OperativeSystems(final String code) {

        this.code = code;
    }

    public static OperativeSystems getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "AND":
                return ANDROID;

            default:
                throw new InvalidParameterException(
                        new StringBuilder().append("Code Received: ").append(code).toString(),
                        "The received code is not valid for the OperativeSystems enum"
                );
        }
    }

    @Override
    public String getCode() {
        return this.code;
    }

}
