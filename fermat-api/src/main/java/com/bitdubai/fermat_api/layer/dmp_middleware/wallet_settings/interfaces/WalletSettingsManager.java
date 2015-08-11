package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_settings.interfaces;

import java.util.UUID;

/**
 * This interface let us manage the settings of a wallet
 *
 * @author Ezequiel Postan (ezequiel.postan@gmail.com)
 */
public interface WalletSettingsManager {

    /**
     * This method gives us the settings of a wallet
     *
     * @param walletIdInTheDevice the identifier of the wallet we want to work with
     * @return the settings of the specified wallet
     */
    public WalletSettings getSettings(UUID walletIdInTheDevice);
}
