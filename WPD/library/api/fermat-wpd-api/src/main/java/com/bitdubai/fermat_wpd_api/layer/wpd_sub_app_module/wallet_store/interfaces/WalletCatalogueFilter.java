package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_store.interfaces;

/**
 * This class represents a predicate to filter the items in the wallet catalogue
 *
 * @author EzequielPostan
 */
public interface WalletCatalogueFilter {

    /**
     * This method is applied on a catalogue item to check if it satisfies a predicate
     *
     * @param newCatalogueItem The catalogue item to analise
     * @return true if the newCatalogue item satisfies the predicate. False in the other case.
     */
    boolean satisfyFilter(WalletStoreCatalogueItem newCatalogueItem);
}
