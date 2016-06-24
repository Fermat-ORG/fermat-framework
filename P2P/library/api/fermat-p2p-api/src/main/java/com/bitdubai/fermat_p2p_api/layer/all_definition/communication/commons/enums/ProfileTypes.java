package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

/**
 * The enum class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileTypes</code>
 * Enums all the platforms to be found on Fermat.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/06/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public enum ProfileTypes implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    ACTOR             ("AC"),
    CLIENT            ("CL"),
    NETWORK_SERVICE   ("NS"),
    NODE              ("NO"),

    ;

    private final String code;

    ProfileTypes(final String code) {

        this.code     = code    ;
    }

    public static ProfileTypes getByCode(final String code) {

        for (ProfileTypes type : ProfileTypes.values()) {
            if(type.getCode().equals(code))
                return type;
        }

        throw new java.security.InvalidParameterException("Code Received: " + code+" - The received code is not valid for the ProfileTypes enum");
    }

    @Override
    public String getCode() {
        return this.code;
    }

}