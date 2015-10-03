package com.bitdubai.fermat_wpd_plugin.layer.network_service.wallet_store.developer.bitdubai.version_1.structure.catalog;

import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.exceptions.CantGetWalletsCatalogException;
import com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.CatalogItem;

import java.util.List;

/**
 * Created by rodrigo on 7/27/15.
 */
public class WalletCatalog implements com.bitdubai.fermat_wpd_api.layer.wpd_network_service.wallet_store.interfaces.WalletCatalog {
    List<? extends CatalogItem> catalogItems;
    int catalogSize;

    @Override
    public List<CatalogItem> getWalletCatalog(int offset, int top) throws CantGetWalletsCatalogException {
        return (List<CatalogItem>) catalogItems;
    }

    @Override
    public int getCatalogSize() {
        return catalogSize;
    }


    @Override
    public void setCatalogSize(int catalogSize) {
        this.catalogSize = catalogSize;
    }

    @Override
    public void setCatalogItems(List<? extends CatalogItem> catalogItemList) {
        this.catalogItems = catalogItemList;
    }


}
