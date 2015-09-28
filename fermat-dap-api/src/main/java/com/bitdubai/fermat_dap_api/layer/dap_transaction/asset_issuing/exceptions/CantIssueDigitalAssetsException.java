package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_issuing.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 14/09/15.
 */
public class CantIssueDigitalAssetsException extends DAPException{

    static final String DEFAULT_MESSAGE = "There was an error Issuing Digital Assets.";

    public CantIssueDigitalAssetsException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE , cause, context, possibleReason);
    }

}
