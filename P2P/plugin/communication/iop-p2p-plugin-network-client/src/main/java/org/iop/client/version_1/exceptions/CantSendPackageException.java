package org.iop.client.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>CantSendPackageException</code>
 * is thrown when there is an error trying to send a package.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 20/04/2016.
 *
 * @author  lnacosta
 * @version 1.0
 * @since   Java JDK 1.7
 */
public class CantSendPackageException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T SEND PACKAGE EXCEPTION";

    public CantSendPackageException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantSendPackageException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantSendPackageException(String context, String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

}
