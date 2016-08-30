package com.fermat_p2p_layer.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.fermat_p2p_layer.version_1.structure.exceptions.CantInitializeP2PLayerDatabaseException</code>
 * is thrown when there is an error trying to initialize network service's database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeP2PLayerDatabaseException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE P2P LAYER DATABASE EXCEPTION";

    public CantInitializeP2PLayerDatabaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeP2PLayerDatabaseException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInitializeP2PLayerDatabaseException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
