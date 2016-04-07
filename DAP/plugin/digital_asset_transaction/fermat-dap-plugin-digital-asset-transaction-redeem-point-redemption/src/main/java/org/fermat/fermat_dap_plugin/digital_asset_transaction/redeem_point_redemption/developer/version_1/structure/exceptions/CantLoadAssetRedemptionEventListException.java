package org.fermat.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.developer.version_1.structure.exceptions;

import org.fermat.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 26/10/15.
 */
public class CantLoadAssetRedemptionEventListException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "There was an error while searching the redeem point redemption event list.";

    //CONSTRUCTORS

    public CantLoadAssetRedemptionEventListException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
