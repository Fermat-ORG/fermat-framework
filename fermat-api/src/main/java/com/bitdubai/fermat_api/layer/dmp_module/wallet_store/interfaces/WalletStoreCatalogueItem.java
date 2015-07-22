package com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.DatailedInformationNotFoundException;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions.WalletIconNotFoundException;


import java.util.UUID;

/**
 * This interface provides us t
 */
public interface WalletStoreCatalogueItem {

    public int getDefaultSizeInBytes();

    public String getDeveloperName();

    public String getDeveloperPublicKey();

    public byte[] getWalletIcon() throws WalletIconNotFoundException;

    public UUID getWalletCatalogId();

    public String getWalletName();

    public WalletStoreDetailedCatalogItem getDetailedCatalogItem() throws DatailedInformationNotFoundException;

}
