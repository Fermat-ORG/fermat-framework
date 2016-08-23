package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletIconException;

import java.net.URL;
import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface CatalogItem {


    /**
     * Catalog Wallet identifiers
     */
    UUID getId(); //Todo: Refactor a String para que acepte PublicKey

    String getName();

    WalletCategory getCategory();


    /**
     * Catalog wallet information to be shown
     */
    String getDescription();

    int getDefaultSizeInBytes();

    byte[] getIcon() throws CantGetWalletIconException;

    URL getpublisherWebsiteUrl();

    /**
     * Detailed information of the item
     */
    DetailedCatalogItem getDetailedCatalogItemImpl() throws CantGetWalletDetailsException;


}
