/*
 * @#CantInitializeCommunicationsNetworkNodeP2PDatabaseException.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.exceptions.CantInitializeCommunicationsNetworkNodeP2PDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCommunicationsNetworkNodeP2PDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE COMMUNICATIONS NETWORK NODE P2P DATABASE EXCEPTION";

    public CantInitializeCommunicationsNetworkNodeP2PDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCommunicationsNetworkNodeP2PDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCommunicationsNetworkNodeP2PDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCommunicationsNetworkNodeP2PDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
