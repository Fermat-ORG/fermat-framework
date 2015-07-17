package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogueException;
import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantPublishWalletInCatalogueException;

import java.util.List;
import java.util.UUID;

/**
 * Created by loui on 18/02/15.
 */
public interface WalletStoreManager {
    public void publishWallet(CatalogueItem catalogueItem) throws CantPublishWalletInCatalogueException;
    public WalletCatalogue getWalletCatalogue() throws CantGetWalletsCatalogueException;
}
