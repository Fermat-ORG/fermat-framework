package com.bitdubai.fermat_dap_plugin.layer.middleware.asset.issuer.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by franklin on 25/09/15.
 */
public class CantPublishAssetException extends FermatException {
    public static final String DEFAULT_MESSAGE = "CAN'T PUBLISH ASSSET STATE NOT DRAFT";

    public CantPublishAssetException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantPublishAssetException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantPublishAssetException(final String message) {
        this(message, null);
    }

    public CantPublishAssetException() {
        this(DEFAULT_MESSAGE);
    }
}
