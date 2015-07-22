package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletCategory;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface CatalogItem {


    /**
     * Catalog Wallet identifiers
     */
    public UUID getWalletCatalogId();
    public String getWalletName();
    public WalletCategory getCategory();


    /**
     * Catalog wallet information to be shown
     */
    public String getWalletDescription();
    public int getDefaultSizeInBytes();
    public byte[] getWalletIcon() throws CantGetWalletIconException;

    /**
     * Developer information
     */
    public String getDeveloperName();
    public String getDeveloperPublicKey();


    /**
     * Detailed information of the item
     */
    public DetailedCatalogItem getDetailedCatalogItem() throws CantGetWalletDetailsException;


}
