package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.NewCatalogueItem;

/**
 * Created by eze on 2015.07.09..
 */
public interface WalletInformation extends NewCatalogueItem {

    public long getNumberOfDownloads();

    public boolean isInstalled();

}
