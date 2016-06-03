package org.fermat.fermat_dap_api.layer.dap_sub_app_module.redeem_point_community.exceptions;

import com.bitdubai.fermat_ccp_api.layer.wallet_module.crypto_wallet.exceptions.CryptoWalletException;

/**
 * The interface <code>CantGetCryptoWalletException</code>
 * is thrown when i cant RETURN the wallet.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 18/06/15.
 *
 * @version 1.0
 */
public class CantGetSupAppRedeemPointModuleException extends CryptoWalletException {

    public static final String DEFAULT_MESSAGE = "CAN'T GET SUB APP MODULE REDEEM POINT REQUESTED EXCEPTION";

    public CantGetSupAppRedeemPointModuleException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantGetSupAppRedeemPointModuleException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantGetSupAppRedeemPointModuleException(final String message) {
        this(message, null);
    }

    public CantGetSupAppRedeemPointModuleException() {
        this(DEFAULT_MESSAGE);
    }
}
