package com.bitdubai.fermat_bch_plugin.layer.asset_vault.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * Created by rodrigo on 9/20/15.
 */
public class VaultKeyHierarchyException extends FermatException {
    public static final String DEFAULT_MESSAGE = "There was an error in the Key hierarchy of the Asset Vault";

    public VaultKeyHierarchyException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
