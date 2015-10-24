/*
 * @#CantInitializeCommunicationsCloudServerP2PDatabaseException.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_dmp_plugin.layer.p2p.communications_cloud_server.developer.bitdubai.version_1.exceptions.CantInitializeCommunicationsCloudServerP2PDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 22/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeCommunicationsCloudServerP2PDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INTIALIZE COMMUNICATIONS CLOUD SERVER P2P DATABASE EXCEPTION";

    public CantInitializeCommunicationsCloudServerP2PDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeCommunicationsCloudServerP2PDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeCommunicationsCloudServerP2PDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeCommunicationsCloudServerP2PDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
}
