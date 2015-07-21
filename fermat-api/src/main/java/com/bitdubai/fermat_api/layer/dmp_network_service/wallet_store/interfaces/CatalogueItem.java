package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletDetailsException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface CatalogueItem {

    public UUID getItemCatalogId();

    public String getWalletName();

    public String getDeveloperName();

    public int getDefaultSizeInBytes();

    public String getDeveloperPublicKey();

    public byte[] getWalletIcon() throws CantGetWalletIconException;

    public String getWalletDescription();

    public DetailedCatalogItem getDetailedCatalogItem() throws CantGetWalletDetailsException;

}
