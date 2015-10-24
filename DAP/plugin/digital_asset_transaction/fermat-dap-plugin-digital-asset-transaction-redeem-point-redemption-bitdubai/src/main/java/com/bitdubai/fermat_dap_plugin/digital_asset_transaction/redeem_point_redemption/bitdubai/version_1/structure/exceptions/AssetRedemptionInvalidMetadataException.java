package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 24/10/15.
 */
public class AssetRedemptionInvalidMetadataException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "The Metadata found during the transaction is no longer valid.";
    public static final String DEFAULT_POSSIBLE_REASON = "The Metadata information has changed during the transaction.";

    //CONSTRUCTORS

    public AssetRedemptionInvalidMetadataException(String context) {
        super(DEFAULT_MESSAGE, null, context, DEFAULT_POSSIBLE_REASON);
    }

    public AssetRedemptionInvalidMetadataException(Exception cause, String context) {
        super(DEFAULT_MESSAGE, cause, context, DEFAULT_POSSIBLE_REASON);
    }


    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
