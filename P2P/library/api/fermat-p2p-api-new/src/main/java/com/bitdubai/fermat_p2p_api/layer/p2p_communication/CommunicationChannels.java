package com.bitdubai.fermat_p2p_api.layer.p2p_communication;

import com.bitdubai.fermat_api.layer.all_definition.enums.interfaces.FermatEnum;
import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The enum <code>com.bitdubai.fermat_p2p_api.layer.p2p_communication.CommunicationChannels</code>
 * enumerates all the possible communication channels in the Fermat System.
 *
 * Created by Leon Acosta (laion.cj91@gmail.com) on 03/05/2015.
 *
 * @version 1.0
 * @since   Java JDK 1.7
 */
public enum CommunicationChannels implements FermatEnum {
    
    P2P_SERVERS     ("P2PSV"),

    ;

    private final String code;

    CommunicationChannels(final String code){
        this.code = code;
    }

    public static CommunicationChannels getByCode(final String code) throws InvalidParameterException {

        for (CommunicationChannels channel : CommunicationChannels.values()) {
            if(channel.getCode().equals(code))
                return channel;
        }

        throw new InvalidParameterException(
                "Code Received: " + code,
                "This code is not valid for the CommunicationChannels enum."
        );
    }

    @Override
    public String getCode(){
        return code;
    }

}
