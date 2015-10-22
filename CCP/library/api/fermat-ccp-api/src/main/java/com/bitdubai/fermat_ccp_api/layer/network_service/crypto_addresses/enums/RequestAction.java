package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The class <code>com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums.RequestAction</code>
 * enumerates the different types of actions that we can make in the crypto addresses network service ccp plugin.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/10/2015.
 */
public enum RequestAction implements FermatEnum {

    ACCEPT ("ACC"),
    DENY   ("DEN"),
    NONE   ("NON"),
    REQUEST("REQ"),

    ;

    private String code;

    RequestAction(String code) {
        this.code = code;
    }

    public static RequestAction getByCode(String code) throws InvalidParameterException {

        switch (code) {

            case "ACC": return ACCEPT ;
            case "DEN": return DENY   ;
            case "NON": return NONE   ;
            case "REQ": return REQUEST;

            default:
                throw new InvalidParameterException(
                        "Code Received: " + code,
                        "This code is not valid for the RequestAction enum."
                );
        }

    }

    @Override
    public String getCode() {
        return this.code;
    }

}