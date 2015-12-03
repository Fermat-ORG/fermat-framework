package com.bitdubai.fermat_dap_api.layer.dap_module.wallet_asset_issuer.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 01/12/15.
 */
public class CantGetAssetStatisticException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "There was an error while searching the asset statistic list.";

    //CONSTRUCTORS

    public CantGetAssetStatisticException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
