package com.bitdubai.fermat_ccp_plugin.layer.wallet_module.fermat_wallet.developer.bitdubai.version_1.exceptions;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CryptoWalletException;

/**
 * Created by natalia on 29/04/16.
 */
public class BlockchainDownloadUpToDateEventHandlerException extends CryptoWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T HANDLER BLOCKCHAIN DWNLOAD EVENT";

    public BlockchainDownloadUpToDateEventHandlerException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }
}
