package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The enum class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.UpdateTypes</code>
 * Enums all the update types that can be done in the communication layer.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public enum UpdateTypes implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    FULL          ("FUL"),
    GEOLOCATION   ("GEO"),
    PARTIAL       ("PAR"),

    ;

    private final String code;

    UpdateTypes(final String code) {

        this.code = code;
    }

    public static UpdateTypes getByCode(final String code) {

        for (UpdateTypes type : UpdateTypes.values()) {
            if(type.getCode().equals(code))
                return type;
        }

        throw new java.security.InvalidParameterException("Code Received: " + code+" - The received code is not valid for the UpdateTypes enum");
    }

    @Override
    public String getCode() {
        return this.code;
    }

}