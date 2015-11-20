package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/23/15.
 */
public enum ConnectionStatus {
    
    CONNECTED("C"),
    DISCONNECTED("D");

    private final String code;

    ConnectionStatus(final String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public static ConnectionStatus getByCode(final String code) throws InvalidParameterException {
        switch (code){
            case "C": return ConnectionStatus.CONNECTED;
            case "D": return ConnectionStatus.DISCONNECTED;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
        }
    }
}
