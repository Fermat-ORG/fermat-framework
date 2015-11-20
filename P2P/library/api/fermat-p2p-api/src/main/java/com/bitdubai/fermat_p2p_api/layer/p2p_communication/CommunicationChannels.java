package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * Created by ciencias on 2/23/15.
 */
public enum CommunicationChannels {
    
    CLOUD("C"),
    LAN("L"),
    BLUETOOH("B"),
    P2P("P"),
    GEO_FENCED_P2P("GFP");

    private final String code;

    CommunicationChannels(final String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public static CommunicationChannels getByCode(final String code) throws InvalidParameterException{
        switch (code){
            case "B": return CommunicationChannels.BLUETOOH;
            case "C": return CommunicationChannels.CLOUD;
            case "GFP": return CommunicationChannels.GEO_FENCED_P2P;
            case "P": return CommunicationChannels.P2P;
            default: throw new InvalidParameterException(InvalidParameterException.DEFAULT_MESSAGE, null, "Code: " + code, null);
        }
    }
    
}
