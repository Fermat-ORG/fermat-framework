package com.bitdubai.fermat_dap_api.layer.all_definition.contracts.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by rodrigo on 9/4/15.
 */
public class CantDefineContractPropertyException extends DAPException {
    public CantDefineContractPropertyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
