package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.network_services.database.exceptions.CantInitializeNetworkServiceDatabaseException</code>
 * is thrown when there is an error trying to initialize network service's database.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/05/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CantInitializeNetworkServiceDatabaseException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE NETWORK SERVICE DATABASE EXCEPTION";

    public CantInitializeNetworkServiceDatabaseException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeNetworkServiceDatabaseException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInitializeNetworkServiceDatabaseException(Exception cause) {
        this(DEFAULT_MESSAGE, cause, null, null);
    }

}
