package com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.DatailedInformationNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.WalletIconNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.CatalogItem;


import java.util.UUID;

/**
 * This interface provides us t
 */
public interface WalletStoreCatalogueItem extends CatalogItem{

    public InstallationStatus getInstallationStatus();

    public WalletStoreDetailedCatalogItem getWalletDetailedCatalogItem() throws DatailedInformationNotFoundException;

}
