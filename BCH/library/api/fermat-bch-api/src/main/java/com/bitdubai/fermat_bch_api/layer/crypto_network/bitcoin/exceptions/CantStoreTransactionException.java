package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_bch_api.layer.crypto_network.blockchain_configuration.BlockchainProvider;

/**
 * Created by rodrigo on 6/29/16.
 */
public class CantStoreTransactionException extends FermatException {

    public static final String DEFAULT_MESSAGE = "There was an error storing the passed transaction.";

    /**
     * includes information about the blockchain provider
     * @param cause
     * @param blockchainProvider
     * @param possibleReason
     */
    public CantStoreTransactionException(Exception cause, BlockchainProvider blockchainProvider, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, blockchainProvider.getName() + " " + blockchainProvider.getActiveBlockchainNetworkType().toString(), possibleReason);
    }

    /**
     * overwrite constructor with all parameters
     * @param message
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantStoreTransactionException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    /**
     * overwrite constructor defining the context
     * @param cause
     * @param context
     * @param possibleReason
     */
    public CantStoreTransactionException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
