package com.bitdubai.fermat_api.layer.dmp_network_service.money_request.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_api.layer.dmp_network_service.money_request.enums.CryptoRequestState</code>
 * describes the state of the reception of the request message
 */
public enum CryptoRequestState {
    PROCESSING ("PCS"),
    RECEIVED_BY_DESTINATION ("RBD"),
    SENT ("SNT");

    private String code;

    CryptoRequestState(String code){
        this.code = code;
    }

    public String getCode(){
        return this.code;
    }

    public CryptoRequestState getByCode(String code) throws InvalidParameterException{
        switch (code){
            case "PCS": return CryptoRequestState.PROCESSING;
            case "RBD": return CryptoRequestState.RECEIVED_BY_DESTINATION;
            case "SNT": return CryptoRequestState.SENT;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE,null,"Code Received: " + code, "This Code Is Not Valid for the CryptoRequestState enum");
        }
    }
}
