package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_statistics.interfaces;

import java.util.UUID;

/**
 * Created by eze on 2015.07.14..
 */
public interface WalletStatistics {

    /**
     * This method tells us the catalogue id of the wallet
     * we are working on.
     *
     * @return The wallet id in the wallet catalogue.
     */
    public UUID getWalletCatalogueId();

    /**
     * This method gives us the number of installations of the represented wallet
     * @return the number of installations of the represented wallet
     */
    public int getNumberOfDownloads();
}
