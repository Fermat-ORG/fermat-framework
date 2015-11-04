package com.bitdubai.fermat_dap_plugin.digital_asset_transaction.redeem_point_redemption.bitdubai.version_1.structure.exceptions;

import com.bitdubai.fermat_api.layer.DAPException;

/**
 * Created by Víctor A. Mars M. (marsvicam@gmail.com) on 27/10/15.
 */
public class CantPersistTransactionMetadataException extends DAPException {

    //VARIABLE DECLARATION

    public static final String DEFAULT_MESSAGE = "There was an exception while persisting the transaction metadata.";

    //CONSTRUCTORS

    public CantPersistTransactionMetadataException(Exception cause, String context, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantPersistTransactionMetadataException(String context, Exception cause) {
        super(DEFAULT_MESSAGE, cause, context, "");
    }

    public CantPersistTransactionMetadataException(String context, Exception cause, String possibleReason) {
        super(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantPersistTransactionMetadataException(String context) {
        super(DEFAULT_MESSAGE, null, context, "");
    }

    public CantPersistTransactionMetadataException(Exception exception) {
        super(exception);
    }

    public CantPersistTransactionMetadataException() {
        this(DEFAULT_MESSAGE);
    }

    //PUBLIC METHODS

    //PRIVATE METHODS

    //GETTER AND SETTERS

    //INNER CLASSES
}
