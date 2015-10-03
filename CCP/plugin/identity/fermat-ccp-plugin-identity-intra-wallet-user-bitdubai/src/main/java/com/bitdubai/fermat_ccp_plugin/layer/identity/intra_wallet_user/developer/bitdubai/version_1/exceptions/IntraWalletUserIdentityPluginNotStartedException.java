package com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.exceptions.IntraWalletUserIdentityPluginNotStartedException</code>
 * is thrown when the plugin is not started..
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/09/2015.
 */
public class IntraWalletUserIdentityPluginNotStartedException extends FermatException {

    public static final String DEFAULT_MESSAGE = "PLUGIN INTRA USER IDENTITY IS NOT STARTED.";

    public IntraWalletUserIdentityPluginNotStartedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public IntraWalletUserIdentityPluginNotStartedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}