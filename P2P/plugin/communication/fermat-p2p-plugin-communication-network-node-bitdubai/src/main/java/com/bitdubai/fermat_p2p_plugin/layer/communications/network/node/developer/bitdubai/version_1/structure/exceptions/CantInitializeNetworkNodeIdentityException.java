package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInitializeNetworkNodeIdentityException</code>
 * is thrown when there is an error trying to initialize the network node identity.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 01/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CantInitializeNetworkNodeIdentityException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE NETWORK NODE IDENTITY EXCEPTION";

    public CantInitializeNetworkNodeIdentityException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeNetworkNodeIdentityException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
