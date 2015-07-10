package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_manager.interfaces;

import java.util.UUID;

/**
 * Created by eze on 2015.07.10..
 */
public interface WalletInstallationInformation {
    public UUID getWalletCatalogId();

    public UUID getResourcesId();

    public UUID getNavigationStructureId();
}
