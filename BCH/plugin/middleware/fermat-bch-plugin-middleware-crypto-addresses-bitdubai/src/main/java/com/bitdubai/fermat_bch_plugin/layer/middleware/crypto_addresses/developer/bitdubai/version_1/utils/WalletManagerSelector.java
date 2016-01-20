package com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.exceptions.CantIdentifyWalletManagerException;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces.WalletManagerManager;

/**
 * The class <code>com.bitdubai.fermat_bch_plugin.layer.middleware.crypto_addresses.developer.bitdubai.version_1.structure.CryptoVaultSelector</code>
 * is intended to contain all the necessary business logic to decide which vault to use.
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/10/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public final class WalletManagerSelector {

    private final WalletManagerManager walletManagerManager;

    public WalletManagerSelector(final WalletManagerManager walletManagerManager) {

        this.walletManagerManager = walletManagerManager;
    }

    public final WalletManagerManager getWalletManager(final Platforms platform) throws CantIdentifyWalletManagerException {
        switch(platform) {

            case CRYPTO_CURRENCY_PLATFORM: return walletManagerManager;
            case DIGITAL_ASSET_PLATFORM:   return walletManagerManager;

            default:
                throw new CantIdentifyWalletManagerException("Unexpected Platform: "+platform.toString()+" - "+ platform.getCode());
        }
    }
}
