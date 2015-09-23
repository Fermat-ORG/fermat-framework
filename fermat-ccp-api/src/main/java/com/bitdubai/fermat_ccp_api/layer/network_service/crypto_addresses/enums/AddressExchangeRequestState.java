package com.bitdubai.fermat_ccp_api.layer.network_service.crypto_addresses.enums;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>AddressExchangeRequestState</code>
 * represents the state of the Contact Request.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 */
public enum AddressExchangeRequestState implements FermatEnum {

    PENDING_REMOTE_RESPONSE ("PRR"),
    PENDING_LOCAL_RESPONSE ("PLR"),
    RESPONDED ("RES");

    private String code;

    AddressExchangeRequestState(String code) {
        this.code = code;
    }

    public static FermatEnum getByCode(String code) throws InvalidParameterException {

        switch (code){
            case "PRR": return PENDING_REMOTE_RESPONSE;
            case "PLR": return PENDING_LOCAL_RESPONSE;
            case "RES": return RESPONDED;

            default: throw new InvalidParameterException(
                    InvalidParameterException.DEFAULT_MESSAGE,
                    null,
                    "Code Received: " + code,
                    "This code is not valid for the AddressExchangeRequestState enum."
            );
        }
    }

    @Override
    public String getCode(){
        return this.code;
    }
}
