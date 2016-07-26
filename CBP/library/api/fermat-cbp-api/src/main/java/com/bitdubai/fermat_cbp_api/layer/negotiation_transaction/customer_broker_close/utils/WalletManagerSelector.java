package com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.exceptions.CantIdentifyWalletManagerException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

/**
 * Created by Yordin Alayn on 27.12.15.
 */
public class WalletManagerSelector {

    private final WalletManagerManager walletManagerManager;

    public WalletManagerSelector(final WalletManagerManager walletManagerManager) {
        this.walletManagerManager = walletManagerManager;
    }

    public final WalletManagerManager getWalletManager(final Platforms platform) throws CantIdentifyWalletManagerException {
        switch (platform) {

            case CRYPTO_CURRENCY_PLATFORM:
                return walletManagerManager;
            case CRYPTO_BROKER_PLATFORM:
                return walletManagerManager;

            default:
                throw new CantIdentifyWalletManagerException(new StringBuilder().append("CBP-NEGOTIATION TRANSACTION-CUSTOMER BROKER CLOSE. Unexpected Platform: ").append(platform.toString()).append(" - ").append(platform.getCode()).toString());

        }
    }
}
