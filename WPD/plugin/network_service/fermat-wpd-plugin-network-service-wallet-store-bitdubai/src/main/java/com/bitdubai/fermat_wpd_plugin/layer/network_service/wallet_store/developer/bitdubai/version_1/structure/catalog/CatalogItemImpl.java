package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletIconException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.CatalogItem;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.DetailedCatalogItem;

import java.net.URL;
import java.util.UUID;

/**
 * Created by rodrigo on 7/23/15.
 */
public class CatalogItemImpl implements CatalogItem {

    UUID id; //Todo: Refactor a String para que acepte PublicKey
    String name;
    WalletCategory category;
    String description;
    int defaultSizeInBytes;
    byte[] icon;
    URL publisherWebsiteUrl;
    DetailedCatalogItemImpl detailedCatalogItemImpl;

    /**
     * Default constructor
     */
    public CatalogItemImpl() {
    }

    /**
     * overloaded constructor
     *
     * @param id
     * @param name
     * @param category
     * @param description
     * @param defaultSizeInBytes
     * @param icon
     * @param detailedCatalogItemImpl
     */
    public CatalogItemImpl(UUID id, String name, WalletCategory category, String description, int defaultSizeInBytes, byte[] icon, DetailedCatalogItemImpl detailedCatalogItemImpl, URL publisherWebsiteUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.defaultSizeInBytes = defaultSizeInBytes;
        this.icon = icon;
        this.publisherWebsiteUrl = publisherWebsiteUrl;
        this.detailedCatalogItemImpl = detailedCatalogItemImpl;
    }

    @Override
    public UUID getId() {
        return id;
    } //Todo: Refactor a String para que acepte PublicKey

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
    public URL getpublisherWebsiteUrl() {
        return publisherWebsiteUrl;
    }

    @Override
    public DetailedCatalogItem getDetailedCatalogItemImpl() throws CantGetWalletDetailsException {
        return detailedCatalogItemImpl;
    }

    public void setWalletCatalogId(UUID id) {
        this.id = id;
    } //Todo: Refactor a String para que acepte PublicKey

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

    public void setDetailedCatalogItemImpl(DetailedCatalogItemImpl detailedCatalogItemImpl) {
        this.detailedCatalogItemImpl = detailedCatalogItemImpl;
    }

    public void setpublisherWebsiteUrl(URL publisherWebsiteUrl) {
        this.publisherWebsiteUrl = publisherWebsiteUrl;
    }

    public void setId(UUID id) {
        this.id = id;
    } //Todo: Refactor a String para que acepte PublicKey

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
        stringBuilder.append("name: ");
        stringBuilder.append(this.name);
        stringBuilder.append(System.lineSeparator());
        stringBuilder.append("Category: ");
        stringBuilder.append(this.category.toString());
        return stringBuilder.toString();
    }
}
