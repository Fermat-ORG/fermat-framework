package com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_api.layer.dmp_network_service.wallet_store.exceptions.CantGetWalletsCatalogueException;

import java.util.List;

/**
 * Created by eze on 2015.07.13..
 */
public interface WalletCatalogue {
    public List<NewCatalogueItem> getWalletCatalogue(int offset, int top) throws CantGetWalletsCatalogueException;
    public void addFilter(WalletCatalogueFilter walletFilter);
    public void clearFilters();

}
