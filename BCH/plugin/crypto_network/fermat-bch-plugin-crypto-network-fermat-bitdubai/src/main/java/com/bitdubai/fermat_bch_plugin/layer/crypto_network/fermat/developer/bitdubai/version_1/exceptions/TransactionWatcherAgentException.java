package com.bitdubai.fermat_bch_plugin.layer.crypto_network.fermat.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 10/16/15.
 */
public class TransactionWatcherAgentException extends FermatException {
    static public final String DEFAULT_MESSAGE = "There was an error executing the Transaction Watcher Agent.";

    public TransactionWatcherAgentException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
