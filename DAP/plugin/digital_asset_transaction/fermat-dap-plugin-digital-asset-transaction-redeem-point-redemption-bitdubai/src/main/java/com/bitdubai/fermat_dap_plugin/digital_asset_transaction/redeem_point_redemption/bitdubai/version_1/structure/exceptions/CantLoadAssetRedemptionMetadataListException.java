package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 27/10/15.
 */
public class CantLoadAssetRedemptionMetadataListException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "There was an exception while retrieving the Metadata Table's records.";

    //CONSTRUCTORS

    public CantLoadAssetRedemptionMetadataListException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantLoadAssetRedemptionMetadataListException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantLoadAssetRedemptionMetadataListException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantLoadAssetRedemptionMetadataListException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantLoadAssetRedemptionMetadataListException(Exception exception) {
        super(exception);
    }

    public CantLoadAssetRedemptionMetadataListException() {
        this(DEFAULT_MESSAGE);
    }
    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
