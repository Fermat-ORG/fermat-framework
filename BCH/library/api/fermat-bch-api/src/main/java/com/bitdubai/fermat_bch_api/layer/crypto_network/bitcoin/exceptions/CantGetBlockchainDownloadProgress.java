package com.bitdubai.fermat_bch_api.layer.crypto_network.bitcoin.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 3/9/16.
 */
public class CantGetBlockchainDownloadProgress extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error getting the download progress of the blockchain.";

    public CantGetBlockchainDownloadProgress(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
