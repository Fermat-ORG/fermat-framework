package org.iop.client.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.client.developer.bitdubai.version_1.exceptions.CantRequestConnectionToExternalNodeException</code>
 * is thrown when there is an error trying to connect with an external node.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CantRequestConnectionToExternalNodeException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T REQUEST CONNECTION TO EXTERNAL NODE EXCEPTION";

    public CantRequestConnectionToExternalNodeException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantRequestConnectionToExternalNodeException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
