package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.common.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.network_service.crypto_payment_request.developer.bitdubai.version_1.network_service.exceptions.CantInitializeNetworkServiceDatabaseException</code>
 * is thrown when there is an error trying to BLABLABLA.
 * <p>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 06/10/2015.
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
