package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 24/10/15.
 */
public class AssetRedemptionBrokenContractException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "The Asset's contract was broken.";
    public static final String DEFAULT_POSSIBLE_REASON = "The expiration date was reach or the asset is no longer redemable.";

    //CONSTRUCTORS

    public AssetRedemptionBrokenContractException(String context) {
        super(DEFAULT_MESSAGE, null, context, DEFAULT_POSSIBLE_REASON);
    }

    public AssetRedemptionBrokenContractException(Exception cause, String context) {
        super(DEFAULT_MESSAGE, cause, context, DEFAULT_POSSIBLE_REASON);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
