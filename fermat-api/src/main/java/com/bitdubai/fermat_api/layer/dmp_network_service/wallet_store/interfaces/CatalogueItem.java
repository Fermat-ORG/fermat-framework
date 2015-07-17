package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletIconException;

import java.util.UUID;

/**
 * Created by eze on 2015.07.09..
 */
public interface CatalogueItem {

    public int getDefaultSizeInBytes();

    public String getDeveloperName();

    public String getDeveloperPublicKey();

    public byte[] getWalletIcon() throws CantGetWalletIconException;

    public UUID getWalletIdInCatalog();

    public String getWalletName();
}
