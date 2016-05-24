package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.exceptions.CantSendMessageException</code>
 * is thrown when there is an error trying to send a message.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/05/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CantSendMessageException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T SEND MESSAGE EXCEPTION";

    public CantSendMessageException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendMessageException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
