/*
* @#CantInitializeNetworkClientP2PDatabaseException.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.network.client.developer.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_p2p_plugin.layer.p2p.network_client.developer.bitdubai.version_1.exceptions.CantInitializeNetworkClientP2PDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 13/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeNetworkClientP2PDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE NETWORK CLIENT P2P DATABASE EXCEPTION";

    public CantInitializeNetworkClientP2PDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeNetworkClientP2PDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeNetworkClientP2PDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeNetworkClientP2PDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}