package com.bidbubai.fermat_dap_plugin.layer.digital_asset_transaction.issuer_redemption.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class CantLoadIssuerRedemptionEventListException extends DAPException {

    //VARIABLE DECLARATION
    public static final String DEFAULT_MESSAGE = "There was an error while searching the issuer redemption event list.";

    //CONSTRUCTORS

    public CantLoadIssuerRedemptionEventListException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantLoadIssuerRedemptionEventListException() {
        super();
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
