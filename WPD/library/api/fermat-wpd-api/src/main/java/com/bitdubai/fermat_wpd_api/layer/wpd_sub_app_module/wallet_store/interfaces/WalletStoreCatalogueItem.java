package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.exceptions.DatailedInformationNotFoundException;

/**
 * This interface provides us t
 */
public interface WalletStoreCatalogueItem extends CatalogItem {

    InstallationStatus getInstallationStatus();

    WalletStoreDetailedCatalogItem getWalletDetailedCatalogItem() throws DatailedInformationNotFoundException;

}
