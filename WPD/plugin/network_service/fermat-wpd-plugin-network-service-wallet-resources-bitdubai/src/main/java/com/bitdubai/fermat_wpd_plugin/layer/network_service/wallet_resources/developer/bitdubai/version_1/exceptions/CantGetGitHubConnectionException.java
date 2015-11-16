package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_resources.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.exceptions.CantGetGitHubConnectionException</code>
 * is thrown when there is an error trying to generate a connection with github.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 16/11/2015.
 */
public class CantGetGitHubConnectionException extends FermatException {

    private static final String DEFAULT_MESSAGE = "CAN'T GET GITHUB CONNECTION EXCEPTION";

    public CantGetGitHubConnectionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetGitHubConnectionException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}
