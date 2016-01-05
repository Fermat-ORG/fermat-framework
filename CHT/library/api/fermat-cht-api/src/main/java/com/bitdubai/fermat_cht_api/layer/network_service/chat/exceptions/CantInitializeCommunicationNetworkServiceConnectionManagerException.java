package com.bitdubai.fermat_cht_api.layer.network_service.chat.exceptions;

import com.bitdubai.fermat_cht_api.all_definition.exceptions.CHTException;

/**
 * Created by Gabriel Araujo on 05/12/15.
 */
public class CantInitializeCommunicationNetworkServiceConnectionManagerException extends CHTException {
    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE COMMUNICATION NETWORK SERVICE CONNECTION MANAGER";

    public CantInitializeCommunicationNetworkServiceConnectionManagerException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
