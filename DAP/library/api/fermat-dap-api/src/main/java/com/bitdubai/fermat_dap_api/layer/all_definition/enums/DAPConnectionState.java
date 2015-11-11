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
    CRYPTO_REQUEST                  ("CYRQT"),
    CRYPTO_REQUEST_FAILURE          ("CYRQF"),
    CRYPTO_RECEIVE                  ("CYRVE"),
    CRYPTO_RECEIVE_PENDING          ("CYRVP"),
    CRYPTO_RECEIVE_FAILURE          ("CYRVF"),
    CONNECTED_REMOTELY              ("CTDRY"),
    DISCONNECTED_REMOTELY           ("DTDRY"),
    REGISTERED_LOCALLY              ("RGDLY"),
    REGISTERED_REMOTELY             ("RGDRY"),
    REGISTERING_LOCALLY_FAILURE     ("RGLYF"),
    REGISTERING_REMOTELY_FAILURE    ("RGRYF"),
    REGISTERING_REMOTELY            ("RGNRY"),
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

            case "CYRQT": return CRYPTO_REQUEST                 ;
            case "CYRQF": return CRYPTO_REQUEST_FAILURE         ;
            case "CYRVE": return CRYPTO_RECEIVE                 ;
            case "CYRVP": return CRYPTO_RECEIVE_PENDING         ;
            case "CYRVF": return CRYPTO_RECEIVE_FAILURE         ;
            case "CTDRY": return CONNECTED_REMOTELY             ;
            case "DTDRY": return DISCONNECTED_REMOTELY          ;
            case "RGDLY": return REGISTERED_LOCALLY             ;
            case "RGDRY": return REGISTERED_REMOTELY            ;
            case "RGLYF": return REGISTERING_LOCALLY_FAILURE    ;
            case "RGRYF": return REGISTERING_REMOTELY_FAILURE   ;
            case "RGNRY": return REGISTERING_REMOTELY           ;

            default: throw new InvalidParameterException(
                    "Code Received: " + code,
                    "The code received is not valid for the DAPConnectionState Enum"
            );
        }
    }
}
