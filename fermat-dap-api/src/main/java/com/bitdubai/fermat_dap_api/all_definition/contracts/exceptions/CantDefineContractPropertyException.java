package com.bitdubai.fermat_dap_api.all_definition.contracts.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/4/15.
 */
public class CantDefineContractPropertyException extends FermatException{
    public CantDefineContractPropertyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
