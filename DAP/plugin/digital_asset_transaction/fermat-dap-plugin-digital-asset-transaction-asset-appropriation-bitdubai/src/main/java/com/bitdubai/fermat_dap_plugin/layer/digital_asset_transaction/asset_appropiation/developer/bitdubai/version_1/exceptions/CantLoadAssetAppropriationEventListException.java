package com.bitdubai.fermat_dap_plugin.layer.digital_asset_transaction.asset_appropiation.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 06/11/15.
 */
public class CantLoadAssetAppropriationEventListException extends DAPException {

    //VARIABLE DECLARATION
    public static final String DEFAULT_MESSAGE = "There was an error while searching the asset appropriation event list.";

    //CONSTRUCTORS

    public CantLoadAssetAppropriationEventListException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
