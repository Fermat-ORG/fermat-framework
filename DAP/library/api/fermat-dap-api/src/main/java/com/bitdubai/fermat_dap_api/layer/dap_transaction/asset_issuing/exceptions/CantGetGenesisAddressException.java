package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 22/09/15.
 */
public class CantGetGenesisAddressException extends DAPException {
    static final String DEFAULT_MESSAGE = "There was an error requesting the Digital Asset genesis address.";

    public CantGetGenesisAddressException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }
}
