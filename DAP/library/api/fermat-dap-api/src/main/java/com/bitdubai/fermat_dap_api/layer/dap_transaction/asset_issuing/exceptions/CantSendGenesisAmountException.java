package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 21/09/15.
 */
public class CantSendGenesisAmountException extends DAPException{
    public static final String DEFAULT_MESSAGE = "There was an error sending the genesis amount.";

    public CantSendGenesisAmountException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
