package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.enums.RegistrationType</code>
 * contains all the possible types of registration in the network node.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 15/04/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum RegistrationType implements FermatEnum {

    /**
     * To make the code more readable, please keep the elements in the Enum sorted alphabetically.
     */

    CHECK_IN  ("IN" ),
    CHECK_OUT ("OUT"),

    ;

    private final String code;

    RegistrationType(final String code) {

        this.code = code;
    }

    public static RegistrationType getByCode(final String code) throws InvalidParameterException {

        for (RegistrationType registrationType : RegistrationType.values()) {
            if(registrationType.getCode().equals(code))
                return registrationType;
        }

        throw new InvalidParameterException(
                "Code Received: " + code,
                "The received code is not valid for the RegistrationType enum."
        );
    }

    @Override
    public String getCode() {

        return this.code;
    }
}