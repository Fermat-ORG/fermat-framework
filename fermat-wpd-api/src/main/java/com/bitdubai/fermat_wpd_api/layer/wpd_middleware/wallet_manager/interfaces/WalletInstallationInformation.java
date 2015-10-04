package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_manager.interfaces;

import java.util.UUID;

/**
 * Created by eze on 2015.07.10..
 */
public interface WalletInstallationInformation {

    /**
     * This method returns the identifier of the wallet to install
     *
     * @return the identifier
     */
    public UUID getWalletCatalogId();

    /**
     * This method tells us the identifier of the selected skin to install
     *
     * @return the identifier
     */
    public UUID getSkinId();

    /**
     * This method gives us the identifier of the language selected to install
     *
     * @return the identifier
     */
    public UUID getLanguageId();
}
