package com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by natalia on 26/01/16.
 */
public class CantGetCommunicationNetworkStatusException extends FermatException {

    public final static String DEFAULT_MESSAGE = "There was an error getting the connection status of the Communication Network.";

    public CantGetCommunicationNetworkStatusException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
