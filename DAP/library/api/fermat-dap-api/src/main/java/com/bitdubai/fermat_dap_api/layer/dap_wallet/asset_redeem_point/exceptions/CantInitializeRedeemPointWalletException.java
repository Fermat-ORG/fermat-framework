package com.bitdubai.fermat_dap_api.layer.dap_wallet.asset_redeem_point.exceptions;

import com.bitdubai.fermat_dap_api.layer.all_definition.exceptions.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 11/01/16.
 */
public class CantInitializeRedeemPointWalletException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "There was an error while attempting to retrieve the RedeemPoint Statistics...";

    //CONSTRUCTORS

    public CantInitializeRedeemPointWalletException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInitializeRedeemPointWalletException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantInitializeRedeemPointWalletException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantInitializeRedeemPointWalletException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantInitializeRedeemPointWalletException(Exception exception) {
        super(exception);
    }

    public CantInitializeRedeemPointWalletException() {
        this(DEFAULT_MESSAGE);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
