package org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 07/09/15.
 */
public class CryptoWalletBalanceInsufficientException extends DAPException {

    static final String DEFAULT_MESSAGE = "The Balance in Crypto Wallet is insufficient to cover Digital Asset Genesis Amount";

    public CryptoWalletBalanceInsufficientException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CryptoWalletBalanceInsufficientException(final String message) {
        this(null, DEFAULT_MESSAGE, message);
    }

}
