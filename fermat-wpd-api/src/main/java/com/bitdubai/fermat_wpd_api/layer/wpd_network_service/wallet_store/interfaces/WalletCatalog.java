package com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces;

import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;

import java.util.List;

/**
 * This interface represents the wallets catalogue
 *
 * @author Ezequiel Postan
 */
public interface WalletCatalog {

    /**
     * This method give us a list of wallets in the catalogue
     *
     * @param offset the offset (position) in the catalogue where we stand
     * @param top the maximum number of wallets to get as a result
     * @return A list of at most "top" catalogue items (wallets)
     * @throws CantGetWalletsCatalogException
     */
    public List<CatalogItem> getWalletCatalog (int offset, int top) throws CantGetWalletsCatalogException;
    public int getCatalogSize() throws CantGetWalletsCatalogException;


    public void setCatalogSize(int catalogSize);
    public void setCatalogItems(List<? extends CatalogItem> catalogItemList);
}
