package org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_2.exceptions.CantInitializeDAPMessageNetworkServiceDatabaseException</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Jose Briceño - (josebricenor@gmail.com) on 18/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeDAPMessageNetworkServiceDatabaseException extends FermatException {

    /**
     * Represent the default message
     */
    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE NETWORK SERVICE DAP MESSAGE TRANSMISSION  DATABASE";

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantInitializeDAPMessageNetworkServiceDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * Constructor with parameters
     *
     * @param message
     * @param cause
     */
    public CantInitializeDAPMessageNetworkServiceDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    /**
     * Constructor with parameter
     *
     * @param message
     */
    public CantInitializeDAPMessageNetworkServiceDatabaseException(final String message) {
        this(message, null);
    }

    /**
     * Constructor with parameter
     *
     * @param exception
     */
    public CantInitializeDAPMessageNetworkServiceDatabaseException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    /**
     * Constructor
     */
    public CantInitializeDAPMessageNetworkServiceDatabaseException() {
        this(DEFAULT_MESSAGE);
    }

}
