package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;

import java.io.Serializable;

/**
 * The enum class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.enums.ProfileStatus</code>
 * Enums all the status of a p2p component profile in Fermat.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/07/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public enum ProfileStatus  implements FermatEnum, Serializable  {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    OFFLINE    ("OF"),
    ONLINE     ("ON"),
    UNKNOWN    ("UN"),

    ;

    private final String code;

    ProfileStatus(final String code) {

        this.code     = code    ;
    }

    public static ProfileStatus getByCode(final String code) {

        for (ProfileStatus type : ProfileStatus.values()) {
            if(type.getCode().equals(code))
                return type;
        }

        return UNKNOWN;
    }

    @Override
    public String getCode() {
        return this.code;
    }

}