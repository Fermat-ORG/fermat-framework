package com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.Catalog;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.*;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.DetailedCatalogItem;

import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public class CatalogItem implements com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces.CatalogItem {
    UUID id;
    String name;
    WalletCategory category;
    String description;
    int defaultSizeInBytes;
    byte[] icon;
    com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItem detailedCatalogItem;

    /**
     * Default constructor
     */
    public CatalogItem() {
    }

    /**
     * overloaded constructor
     * @param id
     * @param name
     * @param category
     * @param description
     * @param defaultSizeInBytes
     * @param icon
     * @param detailedCatalogItem
     */
    public CatalogItem(UUID id, String name, WalletCategory category, String description, int defaultSizeInBytes, byte[] icon, com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItem detailedCatalogItem) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.defaultSizeInBytes = defaultSizeInBytes;
        this.icon = icon;
        this.detailedCatalogItem = detailedCatalogItem;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public WalletCategory getCategory() {
        return category;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getDefaultSizeInBytes() {
        return defaultSizeInBytes;
    }

    @Override
    public byte[] getIcon() throws CantGetWalletIconException {
        return icon;
    }

    @Override
    public DetailedCatalogItem getDetailedCatalogItem() throws CantGetWalletDetailsException {
        return detailedCatalogItem;
    }

    public void setWalletCatalogId(UUID id) {
        this.id= id;
    }

    public void setWalletName(String name) {
        this.name = name;
    }

    public void setCategory(WalletCategory category) {
        this.category = category;
    }

    public void setDefaultSizeInBytes(int defaultSizeInBytes) {
        this.defaultSizeInBytes = defaultSizeInBytes;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

        public void setDetailedCatalogItem(com.bitdubai.fermat_dmp_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog.DetailedCatalogItem detailedCatalogItem) {
        this.detailedCatalogItem = detailedCatalogItem;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Id: ");
        stringBuilder.append(this.id);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Name: ");
        stringBuilder.append(this.name);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Category: ");
        stringBuilder.append(this.category.toString());
        return stringBuilder.toString();
    }
}
