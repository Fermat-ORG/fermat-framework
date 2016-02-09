package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;
/**
 * The Class <code>package com.bitdubai.fermat_cbp_plugin.layer.middleware.matching_engine.developer.bitdubai.version_1.exceptions.CantInitializeMatchingEngineMiddlewareDatabaseException</code>
 * is thrown when an error occurs initializing database
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/16.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeMatchingEngineMiddlewareDatabaseException extends FermatException {

    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE MATCHING ENGINE MIDDLEWARE DATABASE EXCEPTION";

    public CantInitializeMatchingEngineMiddlewareDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeMatchingEngineMiddlewareDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeMatchingEngineMiddlewareDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeMatchingEngineMiddlewareDatabaseException() {
        this(DEFAULT_MESSAGE);
    }

}