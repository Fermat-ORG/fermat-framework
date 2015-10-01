package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.exceptions.CantGetInstallationStatusException;

import java.util.UUID;

/**
 * Created by rodrigo on 7/25/15.
 */
public interface CatalogItemInformation {

    public void setCatalogItemId (CatalogItems catalogItem, UUID itemId);
    public UUID getCatalogItemId (CatalogItems catalogItem);

    public void setInstallationStatus (UUID itemId, InstallationStatus installationStatus);
    public InstallationStatus getInstallationStatus (UUID itemId);

}
