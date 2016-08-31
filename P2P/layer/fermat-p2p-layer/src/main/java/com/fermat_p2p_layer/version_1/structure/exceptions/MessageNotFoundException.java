package com.fermat_p2p_layer.version_1.structure.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.fermat_p2p_layer.version_1.structure.exceptions.MessageNotFoundException</code>
 * is thrown when there is an error trying to find a message which not exists.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 19/08/2016.
 *
 * @author lnacosta
 */
public class MessageNotFoundException extends FermatException {

    private static final String DEFAULT_MESSAGE = "MESSAGE NOT FOUND EXCEPTION";

    public MessageNotFoundException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public MessageNotFoundException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public MessageNotFoundException(String context, String possibleReason) {
        this(null, context, possibleReason);
    }


}
