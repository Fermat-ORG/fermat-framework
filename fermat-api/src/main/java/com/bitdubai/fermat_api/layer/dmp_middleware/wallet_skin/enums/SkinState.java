package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_skin.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.middleware.wallet_skin.enums.SkinState</code>
 * enumerates states of an Skin.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum SkinState {

    DRAFT("draft"),
    VERSIONED("versioned"),
    DISMISSED("dismissed"),
    CLOSED("closed");

    public static SkinState getByCode(String key) throws InvalidParameterException {
        switch(key) {
            case"draft":
                return DRAFT;
            case"versioned":
                return VERSIONED;
            case"dismissed":
                return DISMISSED;
            case"closed":
                return CLOSED;
            default:
                throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code Received: " + key, "This Code Is Not Valid for the Plugins enum");

        }
        //return null;
    }

    private final String code;

    SkinState(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
