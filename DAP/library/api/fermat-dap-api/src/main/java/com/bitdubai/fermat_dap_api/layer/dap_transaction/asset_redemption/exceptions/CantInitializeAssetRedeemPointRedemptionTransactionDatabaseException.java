package com.bitdubai.fermat_dap_api.layer.dap_transaction.asset_redemption.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 23/10/15.
 */
public class CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException extends FermatException {

    //VARIABLE DECLARATION
    public static final String DEFAULT_MESSAGE = "CAN'T INITIALIZE REDEEM POINT REDEMPTION DIGITAL ASSET TRANSACTION DATABASE EXCEPTION";

    //CONSTRUCTORS

    public CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException(final String message) {
        this(message, null);
    }

    public CantInitializeAssetRedeemPointRedemptionTransactionDatabaseException() {
        this(DEFAULT_MESSAGE);
    }
//PUBLIC METHODS

//PRIVATE METHODS

//GETTER AND SETTERS

//INNER CLASSES
}
