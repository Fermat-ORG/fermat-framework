package com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>package CantPersistPrivateKeyException</code>
 * is thrown when i cant persist the private key.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantPersistPrivateKeyException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T PERSIST PRIVATE KEY EXCEPTION";

    public CantPersistPrivateKeyException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}