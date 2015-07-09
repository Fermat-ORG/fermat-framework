package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_statistics;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletStatisticsManager {
    public long getNumberOfDownloads(UUID walletCatalogId);
}
