package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;

import java.util.UUID;

/**
 * Created by rodrigo on 7/25/15.
 */
public interface CatalogItemInformation {

    void setCatalogItemId(CatalogItems catalogItem, UUID itemId);

    UUID getCatalogItemId(CatalogItems catalogItem);

    void setInstallationStatus(UUID itemId, InstallationStatus installationStatus);

    InstallationStatus getInstallationStatus(UUID itemId);

}
