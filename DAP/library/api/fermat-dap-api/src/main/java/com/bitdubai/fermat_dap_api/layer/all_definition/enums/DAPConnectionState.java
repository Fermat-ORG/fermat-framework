package com.bitdubai.fermat_dap_api.layer.all_definition.enums;


import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_dap_api.layer.all_definition.enums.ConnectionState</code>
 * enumerates the states of connection of a common Fermat Actor Asset.
 *
 * Created by Nerio on 10/11/15.
 */
public enum DAPConnectionState implements FermatEnum {

    /**
     * For doing the code more readable, please keep the elements in the Enum sorted alphabetically.
     */
    CONNECTED_ONLINE              ("CTDON"),
    CONNECTED_OFFLINE             ("CTDOF"),
    CONNECTING                    ("CTING"),
    REGISTERED_LOCALLY            ("RGDLY"),
    REGISTERED_ONLINE             ("RGDON"),
    REGISTERED_OFFLINE            ("RGDOF"),
    ;

    private final String code;

    DAPConnectionState(final String code) {
        this.code = code;
    }

    @Override
    public final String getCode() {
        return this.code;
    }

    public static DAPConnectionState getByCode(final String code) throws InvalidParameterException {

        switch (code) {

            case "CTDON": return CONNECTED_ONLINE               ;
            case "CTDOF": return CONNECTED_OFFLINE              ;
            case "CTING": return CONNECTING                     ;
            case "RGDLY": return REGISTERED_LOCALLY             ;
            case "RGDON": return REGISTERED_ONLINE              ;
            case "RGDOF": return REGISTERED_OFFLINE             ;

            default: throw new InvalidParameterException(
                    "Code Received: " + code,
                    "The code received is not valid for the DAPConnectionState Enum"
            );
        }
    }
}
