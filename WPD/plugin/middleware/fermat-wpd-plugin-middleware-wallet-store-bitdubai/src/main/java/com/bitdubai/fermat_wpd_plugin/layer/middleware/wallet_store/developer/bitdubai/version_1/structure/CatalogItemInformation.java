package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_store.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.CatalogItems;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.enums.InstallationStatus;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by rodrigo on 7/25/15.
 */
public class CatalogItemInformation implements com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_store.interfaces.CatalogItemInformation {
    private HashMap<CatalogItems, UUID> itemIds = new HashMap<CatalogItems, UUID>();
    private HashMap<UUID, InstallationStatus> itemInstallationStatus = new HashMap<UUID, InstallationStatus>();


    @Override
    public void setCatalogItemId(CatalogItems catalogItem, UUID itemId) {
        if (itemIds.containsKey(catalogItem))
            itemIds.remove(catalogItem);

        itemIds.put(catalogItem, itemId);
    }

    @Override
    public UUID getCatalogItemId(CatalogItems catalogItem) {
        return itemIds.get(catalogItem);
    }

    @Override
    public void setInstallationStatus(UUID itemId, InstallationStatus installationStatus) {
        itemInstallationStatus.remove(itemId);
        itemInstallationStatus.put(itemId, installationStatus);
    }

    @Override
    public InstallationStatus getInstallationStatus(UUID itemId) {
        return itemInstallationStatus.get(itemId);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WalletId: " + getCatalogItemId(CatalogItems.WALLET));
        stringBuilder.append(" - Status: " + getInstallationStatus(getCatalogItemId(CatalogItems.WALLET)));
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("LanguageId: " + getCatalogItemId(CatalogItems.LANGUAGE));
        stringBuilder.append(" - Status: " + getInstallationStatus(getCatalogItemId(CatalogItems.LANGUAGE)));
        stringBuilder.append(System.lineSeparator());

        stringBuilder.append("SkinId: " + getCatalogItemId(CatalogItems.SKIN));
        stringBuilder.append(" - Status: " + getInstallationStatus(getCatalogItemId(CatalogItems.SKIN)));
        stringBuilder.append(System.lineSeparator());

        return  stringBuilder.toString();
    }
}
