package org.fermat.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/09/15.
 */
public class CantSendGenesisAmountException extends DAPException {
    public static final String DEFAULT_MESSAGE = "There was an error sending the genesis amount.";

    public CantSendGenesisAmountException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }
}
