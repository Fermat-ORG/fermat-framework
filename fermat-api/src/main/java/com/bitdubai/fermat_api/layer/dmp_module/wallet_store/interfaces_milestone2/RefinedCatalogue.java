package com.bitdubai.fermat_api.layer.dmp_module.wallet_store.interfaces_milestone2;

import com.bitdubai.fermat_api.layer.dmp_module.wallet_store.exceptions_milestone2.CantGetWalletsFromCatalogueException;

import java.util.List;

/**
 * Created by eze on 2015.07.14..
 */
public interface RefinedCatalogue {

    /**
     * This method give us a list of wallets in the catalogue
     *
     * @param offset the offset (position) in the catalogue where we stand
     * @param top the maximum number of wallets to get as a result
     * @return A list of at most "top" catalogue items (wallets)
     * @throws CantGetWalletsFromCatalogueException
     */
    public List<FinalCatalogueItem> getWalletCatalogue(int offset, int top) throws CantGetWalletsFromCatalogueException;

    /**
     * This method let us set filters to the catalogue
     * @param walletFilter the filter we want to add
     */
    public void addFilter(WalletCatalogueFilter walletFilter);

    /**
     * This method applies the filters set by "addFilter" to the catalogue in order to only get items
     * that satisfy the said filters.
     */
    public void clearFilters();
}
