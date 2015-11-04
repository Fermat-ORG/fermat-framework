package com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_api.FermatException;

/**
 * The exception <code>com.bitdubai.fermat_ccp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.WalletContactsMiddlewarePluginNotStartedException</code>
 * is thrown when the plugin is not started..
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/09/2015.
 */
public class WalletContactsMiddlewarePluginNotStartedException extends FermatException {

    public static final String DEFAULT_MESSAGE = "PLUGIN WALLET CONTACTS MIDDLEWARE IS NOT STARTED.";

    public WalletContactsMiddlewarePluginNotStartedException(String message, Exception cause, String context, String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public WalletContactsMiddlewarePluginNotStartedException(Exception cause, String context, String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

}