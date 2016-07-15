package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 7/15/16.
 */
public class NetworkMonitorIsNotRunningException extends FermatException {

    private static final String DEFAULT_MESSAGE = "The crypto network monitor is not running for the specified network.";

    private static final String DEFAULT_REASON = "The network is not activated or is being reset just now.";

    private static final String DEFAULT_CONTEXT = "CryptoNetworkManager";

    public NetworkMonitorIsNotRunningException() {
        super(DEFAULT_MESSAGE, null, DEFAULT_CONTEXT, DEFAULT_REASON);
    }

    public NetworkMonitorIsNotRunningException(String context) {
        super(DEFAULT_MESSAGE, null, context, DEFAULT_REASON);
    }

    public NetworkMonitorIsNotRunningException(Exception cause, String context) {
        super(DEFAULT_MESSAGE, cause, context, DEFAULT_REASON);
    }

    public NetworkMonitorIsNotRunningException(String context, String possibleReason) {
        super(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public NetworkMonitorIsNotRunningException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
