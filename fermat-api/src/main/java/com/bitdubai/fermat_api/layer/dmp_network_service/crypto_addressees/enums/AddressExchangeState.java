package com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.dmp_network_service.crypto_addressees.enums.AddressExchangeState</code>
 * represents the state of the exchange of addresses between two intra users.
 */
public enum AddressExchangeState {
    ADDRESS_SENT ("SNT"),
    BEING_PROCESSED ("BPC"),
    EXCHANGED_SUCCESSFUL ("ESS");

    private String code;

    AddressExchangeState (String code) {
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public static AddressExchangeState getByCode(String code) throws InvalidParameterException {
        switch (code){
            case "SNT": return AddressExchangeState.ADDRESS_SENT;
            case "BPC": return AddressExchangeState.BEING_PROCESSED;
            case "ESS": return AddressExchangeState.EXCHANGED_SUCCESSFUL;
            default: throw new InvalidParameterException();
        }
    }
}
